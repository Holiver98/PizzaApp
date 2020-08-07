package com.github.holiver98.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * "Order" is a reserved word in h2 database!
 */
@Entity(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "database_seq")
    @SequenceGenerator(name="database_seq", sequenceName = "orders_tb_seq",
    initialValue = 1, allocationSize = 1)
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
    @Column(name = "order_date")
    private Date date;
    @Column(name = "total_price")
    private BigDecimal totalPrice = BigDecimal.valueOf(0);
}
