package com.github.holiver98.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * "Order" is a reserved word in h2 database!
 */
@Entity(name = "PizzaOrder")
@Data
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private String userEmailAddress;
    @ManyToMany
    private List<Pizza> pizzas;
    private Date date;
    private float totalPrice;
}
