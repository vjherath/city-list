package com.example.citylist.auth.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table( name = AppUser.TABLE_NAME )
public class AppUser {

    public static final String TABLE_NAME = "APP_USER";

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id", nullable = false )
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userRoles;

    private String fullName;

    private boolean enable = true;
}
