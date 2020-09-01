package com.github.holiver98.model;

public enum IngredientType {
    PIZZA_TOPPING,
    PIZZA_BASESAUCE;

    @Override
    public String toString() {
        if(this.equals(IngredientType.PIZZA_TOPPING)){
            return "Pizza topping";
        }else if(this.equals(IngredientType.PIZZA_BASESAUCE)){
            return "Pizza base sauce";
        }else{
            return "";
        }
    }
}
