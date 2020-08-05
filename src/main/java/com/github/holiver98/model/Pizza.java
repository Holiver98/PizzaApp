package com.github.holiver98.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Set;

@Entity(name = "pizzas")
@Data
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "database_seq")
    @SequenceGenerator(name="database_seq", sequenceName = "pizzas_tb_seq",
            initialValue = 1, allocationSize = 1)
    private Long id;
    private String name;
    @JoinTable(
            name="pizzas_ingredients",
            joinColumns =
            @JoinColumn(name = "pizza_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "ingredient_name", referencedColumnName = "name")
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Ingredient> ingredients;
    @Enumerated(EnumType.STRING)
    @Column(name = "pizza_size")
    private PizzaSize size;
    @Column(name = "rating_average")
    private float ratingAverage;
    private float price;
    @Column(name = "custom")
    private boolean isCustom;
}
