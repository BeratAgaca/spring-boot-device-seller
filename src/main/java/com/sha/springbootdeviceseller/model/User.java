package com.sha.springbootdeviceseller.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="users") // not user => because it is reserved
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;


    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    //it wont be a persisten field, it wont be stored on the database, it will be used only as on-the-fly istances.
    @Transient
    private String token;
}
