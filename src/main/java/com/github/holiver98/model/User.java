package com.github.holiver98.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "users")
@Data
public class User {
    @Id
    @Column(name = "email")
    private String emailAddress;
    private String username;
    @Column(name = "user_password")
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role = Role.CUSTOMER;
    @Column(name = "is_logged_in")
    @Deprecated
    private boolean isLoggedIn = false;
}
