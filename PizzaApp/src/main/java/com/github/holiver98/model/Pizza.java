package com.github.holiver98.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Pizza {
    @Id
    @GeneratedValue
    private long id = -1;
    private String name;
    @OneToMany
    private Set<Ingredient> ingredients;
    @Enumerated(EnumType.STRING)
    private PizzaSize size;
    private float ratingAverage;
    private float price;
    private boolean isCustom;
}
