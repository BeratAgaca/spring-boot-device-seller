package com.sha.springbootdeviceseller.repository;

import com.sha.springbootdeviceseller.model.Role;
import com.sha.springbootdeviceseller.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //findBy + className + fieldName
    Optional<User> findByUsername(String username);

    //hql query
    @Modifying
    @Query("update User set role = :role where username = :username")
    void updateUserRole(@Param("username") String username, @Param("role") Role role);
}
