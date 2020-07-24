package com.github.holiver98.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Pizza {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Ingredient> ingredients;
    @Enumerated(EnumType.STRING)
    private PizzaSize size;
    @Column(name = "rating_average")
    private float ratingAverage;
    private float price;
    @Column(name = "custom")
    private boolean isCustom;
}
