package com.sha.springbootdeviceseller.security.jwt;


import com.sha.springbootdeviceseller.security.UserPrincipal;
import com.sha.springbootdeviceseller.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component //we'll call jwt properties from application-default.properties with value annotation
public class JwtProviderImpl implements JwtProvider{

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in.ms}")
    private Long JWT_EXPIRATION_IN_MS;

    @Override
    public String generateToken(UserPrincipal auth){
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(auth.getUsername())
                .claim("roles", authorities)
                .claim("userId", auth.getId())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request){
        Claims claims = extractClaims(request);

        if(claims == null){
            return null;
        }

        String username = claims.getSubject();
        Long userId = claims.get("userId", Long.class);

        /*Bu kod bir JWT (JSON Web Token) üzerindeki rolleri çözerek bir yetki listesi oluşturuyor. İşte bu kodun yaptığı adımları açıklayalım:

        İlk olarak, claims olarak adlandırılan bir nesnenin üzerinde çalışıyoruz. Bu nesne, JWT'deki talepleri (claims) temsil eder. JWT, kullanıcının kimlik doğrulama ve yetkilendirme bilgilerini içeren bir yapıdır.

        İçindeki "roles" talebini alıyoruz. "roles" talebi, kullanıcının sahip olduğu rollerin bir liste şeklinde içerisinde olduğu bir JWT'de sıklıkla bulunur.

        claims.get("roles") ifadesiyle, JWT içindeki "roles" talebini alıyoruz ve bunu bir dize (string) olarak elde ediyoruz.

        Aldığımız dizeyi split(",") fonksiyonuyla virgüllerden böleriz. Bu, dizedeki rolleri ayrı ayrı elemanlara ayırır.

        Daha sonra, Arrays.stream(...) ifadesiyle elde edilen rolleri bir akışa (stream) dönüştürüyoruz. Böylece roller üzerinde işlemler yapmak için bir akış oluşturmuş oluruz.

        map(SecurityUtils::convertToAuthority) ifadesi, her role (rol) için SecurityUtils sınıfındaki convertToAuthority metodu çağrılır. Bu metot, her bir rolü bir yetkiye (authority) dönüştürmek için kullanılır.

        Son olarak, collect(Collectors.toSet()) ifadesiyle akıştaki yetkileri bir küme (Set) şeklinde toplarız. Bu, benzersiz yetkilerin bir koleksiyonunu elde etmemizi sağlar.

        Sonuç olarak, bu kod JWT içindeki "roles" talebini alır, rolleri ayırır, her bir rolü bir yetkiye dönüştürür ve son olarak bu yetkileri bir küme olarak toplar. Bu yetkiler, kullanıcının uygulama içinde hangi işlemleri gerçekleştirebileceğini temsil eder.
        */
        Set<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                .map(SecurityUtils::convertToAuthority)
                .collect(Collectors.toSet());

        UserDetails userDetails = UserPrincipal.builder()
                .username(username)
                .authorities(authorities)
                .id(userId)
                .build();
        if(username == null){
            return null;
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    @Override
    public boolean isTokenValid(HttpServletRequest request){
        Claims claims = extractClaims(request);

        if(claims == null){
            return false;
        }

        if(claims.getExpiration().before(new Date())){
            return false;
        }
        return true;

    }




    private Claims extractClaims(HttpServletRequest request){
        String token = SecurityUtils.extractAuthTokenFromRequest(request);

        if(token == null){
            return null;
        }

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
