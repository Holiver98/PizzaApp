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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    private String userEmailAddress;
    @JoinTable(
            name="orders_pizzas",
            joinColumns =
                @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns =
                @JoinColumn(name = "pizza_id", referencedColumnName = "id")
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Pizza> pizzas;
    private Date date;
    private float totalPrice;
}
