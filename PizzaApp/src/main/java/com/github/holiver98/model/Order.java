package com.github.holiver98.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * "Order" is a reserved word in h2 database!
 */
@Entity(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "email")
    private String userEmailAddress;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Pizza> pizzas;
    private Date date;
    private float totalPrice;
}
