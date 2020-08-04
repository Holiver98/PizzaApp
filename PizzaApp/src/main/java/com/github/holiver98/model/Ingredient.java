package com.github.holiver98.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity(name = "ingredients")
@Data
public class Ingredient {
    @Id
    private String name;
    @Enumerated(EnumType.STRING)
    private IngredientType type;

    //TODO: nyugodtan lehet BigDecimal az érték típusa.
    private float price;
}
