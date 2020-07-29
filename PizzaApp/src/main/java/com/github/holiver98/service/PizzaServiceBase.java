package com.github.holiver98.service;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.IngredientType;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.Rating;
import java.math.BigDecimal;
import java.util.List;

public abstract class PizzaServiceBase implements IPizzaService {
    protected static final int minIngredients = 1;
    protected static final int maxIngredients = 5;
    protected static final int maxBaseSauce = 1;
    protected static final int minBaseSauce = 1;

    protected abstract List<Rating> getRatingsOfPizza(long pizzaId);
    @Override
    public abstract long savePizza(Pizza pizza);
    @Override
    public abstract List<Pizza> getPizzas();
    @Override
    public abstract List<Pizza> getBasicPizzas();
    @Override
    public abstract Pizza getPizzaById(long pizzaId) throws NotFoundException;
    @Override
    public abstract void updatePizza(Pizza pizza) throws NotFoundException;
    @Override
    public abstract void deletePizza(long pizzaId);

    public static boolean isValidPizza(Pizza pizza){
        try {
            checkIfPizzaIsValid(pizza);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    public static void checkIfPizzaIsValid(Pizza pizza) {
        if(pizza == null){
            throw new NullPointerException("pizza was null");
        }

        boolean hasUninitializedFields = pizza.getName() == null ||
                pizza.getIngredients() == null ||
                pizza.getSize() == null;
        if(hasUninitializedFields){
            throw new IllegalArgumentException("pizza has uninitialized fields");
        }

        boolean hasInvalidNumberOfIngredients = pizza.getIngredients().size() < minIngredients ||
                pizza.getIngredients().size() > maxIngredients;
        boolean hasInvalidNumberOfBasesauces = !hasValidNumberOfBasesauces(pizza);
        boolean hasInvalidIngredientPrices = !areIngredientPricesValid(pizza);
        if(hasInvalidNumberOfBasesauces ||
                hasInvalidNumberOfIngredients ||
                hasInvalidIngredientPrices ||
                pizza.getPrice() < 0.0f){
            throw new IllegalArgumentException("invalid pizza");
        }
    }

    @Override
    public float calculatePrice(Pizza pizza) {
        if(!isValidPizza(pizza)){
            return -1;
        }

        BigDecimal totalPrice = new BigDecimal(0);
        for (Ingredient ingredient: pizza.getIngredients()) {
            BigDecimal ingredientPrice = new BigDecimal(Float.toString(ingredient.getPrice()));
            totalPrice = totalPrice.add(ingredientPrice);
        }

        return totalPrice.floatValue();
    }

    @Override
    public float recalculateRatingAverage(long pizzaId) throws NotFoundException {
        Pizza pizzaToUpdate = getPizzaById(pizzaId);
        List<Rating> ratings = getRatingsOfPizza(pizzaId);
        float newRatingAverage = calculateNewRatingAverage(ratings);
        pizzaToUpdate.setRatingAverage(newRatingAverage);
        updatePizza(pizzaToUpdate);
        return newRatingAverage;
    }

    private float calculateNewRatingAverage(List<Rating> ratings) {
        if(ratings.size() == 0){
            return 0;
        }

        int ratingSum = 0;
        for (Rating rating : ratings) {
            ratingSum += rating.getRating();
        }
        return (float)ratingSum / (float)ratings.size();
    }

    private static boolean areIngredientPricesValid(Pizza pizza) {
        for (Ingredient i: pizza.getIngredients()) {
            if(i.getPrice() < 0f){
                return false;
            }
        }
        return true;
    }

    private static boolean hasValidNumberOfBasesauces(Pizza pizza){
        int numberOfBaseSauces = 0;
        for (Ingredient ingredient: pizza.getIngredients()) {
            boolean ingredientIsBasesauce = ingredient.getType().equals(IngredientType.PIZZA_BASESAUCE);
            if(ingredientIsBasesauce){
                numberOfBaseSauces++;
            }
        }

        boolean hasValidNumberOfBasesauces = numberOfBaseSauces <= maxBaseSauce && numberOfBaseSauces >= minBaseSauce;
        return hasValidNumberOfBasesauces;
    }
}
