package com.github.holiver98.service.jpa;

import com.github.holiver98.dal.jpa.IIngredientRepository;
import com.github.holiver98.dal.jpa.IPizzaRepository;
import com.github.holiver98.dal.jpa.IRatingRepository;
import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.Rating;
import com.github.holiver98.service.IPizzaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class JpaPizzaService implements IPizzaService {
    @Autowired
    private IIngredientRepository ingredientRepository;
    @Autowired
    private IPizzaRepository pizzaRepository;
    @Autowired
    private IRatingRepository ratingRepository;

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
        Optional<Pizza> optionalPizzaToUpdate = pizzaRepository.findById(pizzaId);
        Pizza pizzaToUpdate;
        if(!optionalPizzaToUpdate.isPresent()){
            System.out.println("Pizza with id("+ pizzaId +") was not found in the database!");
            return -1;
        }else{
            pizzaToUpdate = optionalPizzaToUpdate.get();
        }

        List<Rating> ratings = ratingRepository.findByPizzaId(pizzaId);
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
        pizzaRepository.save(pizzaToUpdate);//update

        return newRatingAverage;
    }

    @Override
    public long savePizza(Pizza pizza) {
        if(!isValidPizza(pizza)){
            return -1;
        }

        if(pizzaRepository.existsById(pizza.getId())){
            System.out.println("Pizza already exists!");
            return -1;
        }

        return pizzaRepository.save(pizza).getId();
    }

    @Override
    public List<Pizza> getPizzas() {
        return pizzaRepository.findAll();
    }

    @Override
    public List<Pizza> getBasicPizzas() {
        return pizzaRepository.findByIsCustom(false);
    }

    @Override
    public Pizza getPizzaById(long pizzaId) {
        if(pizzaId < 0){
            return null;
        }else{
            return pizzaRepository.findById(pizzaId).orElse(null);
        }
    }

    @Override
    public void updatePizza(Pizza pizza) {
        if(!isValidPizza(pizza)){
            return;
        }

        if(!pizzaRepository.existsById(pizza.getId())){
            System.out.println("Pizza doesn't exist!");
            return;
        }

        pizzaRepository.save(pizza);
    }

    @Override
    public void deletePizza(long pizzaId) {
        if(pizzaId < 0){
            return;
        }

        pizzaRepository.deleteById(pizzaId);
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
        }else if(pizza.getId() == null){
            return false;
        }else if(!arePricesValid(pizza)){
            return false;
        }

        return true;
    }
}
