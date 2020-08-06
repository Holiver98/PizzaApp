package com.github.holiver98.service;

import com.github.holiver98.model.*;

import java.math.BigDecimal;
import java.util.List;

public abstract class PizzaServiceBase implements IPizzaService {
    protected IUserService userService;
    protected static final int minIngredients = 1;
    protected static final int maxIngredients = 5;
    protected static final int maxBaseSauce = 1;
    protected static final int minBaseSauce = 1;

    protected abstract long doSavePizza(Pizza pizza);
    protected abstract void doUpdatePizza(Pizza pizza) throws NotFoundException;
    protected abstract void doDeletePizza(long pizzaId);
    protected abstract boolean pizzaExists(Pizza pizza);

    @Override
    public abstract List<Pizza> getPizzas();
    @Override
    public abstract List<Pizza> getBasicPizzas();
    @Override
    public abstract Pizza getPizzaById(long pizzaId) throws NotFoundException;

    public PizzaServiceBase(IUserService userService){
        this.userService = userService;
    }

    @Override
    public long savePizza(Pizza pizza) throws AlreadyExistsException {
        checkIfPizzaIsValid(pizza);
        if(pizzaExists(pizza)){
            throw new AlreadyExistsException("Pizza already exists!");
        }
        User user = getLoggedInUserOrThrowException("You have to be logged in to save pizza!");
        ifUserIsNotChefThrowException(user, "You have to have Chef role to save pizza!");
        return doSavePizza(pizza);
    }

    @Override
    public void updatePizzaWithoutAuthentication(Pizza pizza) throws NotFoundException {
        checkIfPizzaIsValid(pizza);
        checkIfPizzaExists(pizza);
        doUpdatePizza(pizza);
    }

    @Override
    public void updatePizza(Pizza pizza) throws NotFoundException {
        checkIfPizzaIsValid(pizza);
        checkIfPizzaExists(pizza);
        User user = getLoggedInUserOrThrowException("You have to be logged in to update pizza!");
        ifUserIsNotChefThrowException(user, "You have to have Chef role to update pizza!");
        doUpdatePizza(pizza);
    }

    @Override
    public void deletePizza(long pizzaId) {
        User user = getLoggedInUserOrThrowException("You have to be logged in to delete pizza!");
        ifUserIsNotChefThrowException(user, "You have to have Chef role to delete pizza!");
        doDeletePizza(pizzaId);
    }

    public static boolean isValidPizza(Pizza pizza){
        try {
            checkIfPizzaIsValid(pizza);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    /**
     * Throws exceptions if the pizza is invalid.
     */
    public static void checkIfPizzaIsValid(Pizza pizza) {
        if(pizza == null){
            throw new NullPointerException("pizza was null");
        }
        checkIfFieldsAreInitialized(pizza);
        checkIfHasValidNumberOfBasesauces(pizza);
        checkIfHasValidNumberOfIngredients(pizza);
        checkIfHasValidIngredientPrices(pizza);
        checkIfPizzaPriceIsNotNegative(pizza);
    }

    @Override
    public float calculatePrice(Pizza pizza) {
        checkIfPizzaIsValid(pizza);

        BigDecimal totalPrice = new BigDecimal(0);
        for (Ingredient ingredient: pizza.getIngredients()) {
            BigDecimal ingredientPrice = new BigDecimal(Float.toString(ingredient.getPrice()));
            totalPrice = totalPrice.add(ingredientPrice);
        }

        return totalPrice.floatValue();
    }

    private void checkIfPizzaExists(Pizza pizza) throws NotFoundException {
        if(!pizzaExists(pizza)){
            throw new NotFoundException("Pizza with id (" + pizza.getId() + ") doesn't exist!");
        }
    }

    private static void checkIfFieldsAreInitialized(Pizza pizza) {
        boolean hasUninitializedFields = pizza.getName() == null ||
                pizza.getIngredients() == null ||
                pizza.getSize() == null;
        if(hasUninitializedFields){
            throw new IllegalArgumentException("invalid pizza: has uninitialized fields");
        }
    }

    private static void checkIfHasValidNumberOfBasesauces(Pizza pizza) {
        if(!hasValidNumberOfBasesauces(pizza)){
            throw new IllegalArgumentException("invalid pizza: invalid number of base sauces");
        }
    }

    private static void checkIfHasValidNumberOfIngredients(Pizza pizza) {
        boolean hasInvalidNumberOfIngredients = pizza.getIngredients().size() < minIngredients ||
                pizza.getIngredients().size() > maxIngredients;
        if(hasInvalidNumberOfIngredients){
            throw new IllegalArgumentException("invalid pizza: invalid number of ingredients");
        }
    }

    private static void checkIfHasValidIngredientPrices(Pizza pizza) {
        if(!areIngredientPricesValid(pizza)){
            throw new IllegalArgumentException("invalid pizza: invalid ingredient prices");
        }
    }

    private static void checkIfPizzaPriceIsNotNegative(Pizza pizza) {
        if(pizza.getPrice() < 0.0f){
            throw new IllegalArgumentException("invalid pizza: invalid price");
        }
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

    private User getLoggedInUserOrThrowException(String exceptionMessage){
        User user = userService.getLoggedInUser();
        if(user == null){
            throw new NoPermissionException(exceptionMessage);
        }
        return user;
    }

    private void ifUserIsNotChefThrowException(User user, String exceptionMessage){
        if(!user.getRole().equals(Role.CHEF)){
            throw new NoPermissionException(exceptionMessage);
        }
    }
}
