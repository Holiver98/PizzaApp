package com.github.holiver98.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class User {
    @Id
    @Column(name = "email")
    private String emailAddress;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
}
