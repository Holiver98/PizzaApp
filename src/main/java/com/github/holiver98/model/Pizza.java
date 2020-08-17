package com.github.holiver98.model;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
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
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Ingredient> ingredients;
    @Enumerated(EnumType.STRING)
    @Column(name = "pizza_size")
    private PizzaSize size;
    @Column(name = "rating_average")
    private BigDecimal ratingAverage = BigDecimal.valueOf(0);
    private BigDecimal price = BigDecimal.valueOf(0);
    @Column(name = "custom")
    private boolean isCustom;
}
