package com.github.holiver98.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Order {
    @Id
    @GeneratedValue
    private long id = -1;
    private String userEmailAddress;
    @OneToMany
    private List<Pizza> pizzas;
    private Date date;
    private float totalPrice;
}
