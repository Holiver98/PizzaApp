package com.github.holiver98.service.inmemory;

import com.github.holiver98.dal.inmemory.IInMemoryIngredientDao;
import com.github.holiver98.dal.inmemory.IInMemoryPizzaDao;
import com.github.holiver98.dal.inmemory.IInMemoryRatingDao;
import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.Rating;
import com.github.holiver98.service.IPizzaService;

import java.math.BigDecimal;
import java.util.List;

public class InMemoryPizzaService implements IPizzaService {

    private IInMemoryIngredientDao ingredientDao;
    private IInMemoryPizzaDao pizzaDao;
    private IInMemoryRatingDao ratingDao;

    public InMemoryPizzaService(IInMemoryPizzaDao pizzaDao, IInMemoryIngredientDao ingredientDao, IInMemoryRatingDao ratingDao){
        this.pizzaDao = pizzaDao;
        this.ingredientDao = ingredientDao;
        this.ratingDao = ratingDao;
    }

    @Override
    public float calculatePrice(Pizza pizza) {
        if(pizza == null){
            return -1f;
        }

        if(pizza.getIngredients() == null){
            System.out.println("Ingredients list not initialized!");
            return -1f;
        }

        if(pizza.getIngredients().size() == 0){
            System.out.println("There are no ingredients assigned to this pizza!");
            return -1f;
        }

        if(!arePricesValid(pizza)){
            System.out.println("Invalid ingredient prices!");
            return -1f;
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
        Pizza pizzaToUpdate = pizzaDao.getPizzaById(pizzaId);
        if(pizzaToUpdate == null){
            System.out.println("Pizza with id("+ pizzaId +") was not found in the database!");
            return -1;
        }

        List<Rating> ratings = ratingDao.getRatingsOfPizza(pizzaId);
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
        pizzaDao.updatePizza(pizzaToUpdate);

        return newRatingAverage;
    }

    @Override
    public long savePizza(Pizza pizza) {
        if(!isValidPizza(pizza)){
            return -1;
        }

        return pizzaDao.savePizza(pizza);
    }

    @Override
    public List<Pizza> getPizzas() {
        return pizzaDao.getPizzas();
    }

    @Override
    public List<Pizza> getBasicPizzas() {
        return pizzaDao.getBasicPizzas();
    }

    @Override
    public Pizza getPizzaById(long pizzaId) {
        if(pizzaId < 0){
            return null;
        }else{
            return pizzaDao.getPizzaById(pizzaId);
        }
    }

    @Override
    public void updatePizza(Pizza pizza) {
        if(!isValidPizza(pizza)){
            return;
        }

        pizzaDao.updatePizza(pizza);
    }

    @Override
    public void deletePizza(long pizzaId) {
        if(pizzaId < 0){
            return;
        }

        pizzaDao.deletePizza(pizzaId);
    }

    private boolean arePricesValid(Pizza pizza) {
        for (Ingredient i: pizza.getIngredients()) {
            if(i.getPrice() < 0f){
                return false;
            }
        }

        return true;
    }

    private boolean isValidPizza(Pizza pizza){
        if(pizza == null){
            return false;
        }else if(pizza.getId() < 0){
            return false;
        }else if(!arePricesValid(pizza)){
            return false;
        }

        return true;
    }
}
