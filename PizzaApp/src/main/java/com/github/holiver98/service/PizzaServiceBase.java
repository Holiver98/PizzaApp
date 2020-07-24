package com.github.holiver98.service;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.Rating;
import java.math.BigDecimal;
import java.util.List;

public abstract class PizzaServiceBase implements IPizzaService {
    @Override
    public float calculatePrice(Pizza pizza) {
        if(!isValidPizza(pizza)){
            return -1;
        }

        BigDecimal totalPrice = new BigDecimal(0);
        for (Ingredient i: pizza.getIngredients()) {
            BigDecimal ingredientPrice = new BigDecimal(Float.toString(i.getPrice()));
            totalPrice = totalPrice.add(ingredientPrice);
        }

        return totalPrice.floatValue();
    }

    @Override
    public float recalculateRatingAverage(long pizzaId) {
        Pizza pizzaToUpdate = getPizzaById(pizzaId);
        if(pizzaToUpdate == null){
            System.out.println("Pizza with id("+ pizzaId +") was not found in the database!");
            return -1;
        }

        List<Rating> ratings = getRatingsOfPizza(pizzaId);
        int ratingSumm = 0;
        for (Rating rating : ratings) {
            ratingSumm += rating.getRating();
        }

        float newRatingAverage;
        if(ratings.size() == 0){
            return 0;
        }else{
            newRatingAverage = (float)ratingSumm / (float)ratings.size();
        }

        pizzaToUpdate.setRatingAverage(newRatingAverage);
        updatePizza(pizzaToUpdate);

        return newRatingAverage;
    }

    protected abstract List<Rating> getRatingsOfPizza(long pizzaId);
    @Override
    public abstract long savePizza(Pizza pizza);
    @Override
    public abstract List<Pizza> getPizzas();
    @Override
    public abstract List<Pizza> getBasicPizzas();
    @Override
    public abstract Pizza getPizzaById(long pizzaId);
    @Override
    public abstract void updatePizza(Pizza pizza);
    @Override
    public abstract void deletePizza(long pizzaId);

    protected boolean arePricesValid(Pizza pizza) {
        for (Ingredient i: pizza.getIngredients()) {
            if(i.getPrice() < 0f){
                return false;
            }
        }

        return true;
    }

    protected boolean isValidPizza(Pizza pizza){
        if(pizza == null){
            return false;
        }else if(pizza.getId() == null){
            return false;
        }else if(!arePricesValid(pizza)){
            System.out.println("Invalid ingredient prices!");
            return false;
        }else if(pizza.getIngredients() == null){
            System.out.println("Ingredients list not initialized!");
            return false;
        }else if(pizza.getIngredients().size() == 0){
            System.out.println("There are no ingredients assigned to this pizza!");
            return false;
        }

        return true;
    }
}
