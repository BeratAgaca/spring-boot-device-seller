package com.sha.springbootdeviceseller.security;

import com.sha.springbootdeviceseller.model.Role;
import com.sha.springbootdeviceseller.security.jwt.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration //because we'll describe some beans in this class
@EnableWebSecurity //this annotation tells to spring security that this class is the web security configuration class
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(); //because we'll use different origins on the backend and frontend
        http.csrf().disable(); //one-click attack or session writing
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //it is guaranteed way that the application will not create any session at all.
        //So each request needs to be reauthenticated because we'll use JWT as authentication.
        http.authorizeRequests()
                .antMatchers("/api/authentication/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/device").permitAll()
                .antMatchers("api/device/**").hasRole(Role.ADMIN.name())
                .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }


    //Why don't we describe it as a component. Because of scope.
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(){
        return new JwtAuthorizationFilter();
    }




    //create authentication manager be
    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean //is used to create new instances and default instances are singleton. So this will be created one time and we can reach it throughout the application
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //we'll allow all origins for testings
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*");
            }
        };
    }

}
