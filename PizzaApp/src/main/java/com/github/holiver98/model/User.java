package com.github.holiver98.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class User {
    @Id
    private String emailAddress;
    private String username;
    private String password;
}
