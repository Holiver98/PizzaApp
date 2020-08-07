package com.github.holiver98.service;

import com.github.holiver98.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public abstract class PizzaServiceBase implements IPizzaService {
    protected IUserService userService;
    protected static final int minIngredients = 1;
    protected static final int maxIngredients = 5;
    protected static final int maxBaseSauce = 1;
    protected static final int minBaseSauce = 1;

    protected abstract Optional<Pizza> doSavePizza(Pizza pizza);
    protected abstract int doUpdatePizza(Pizza pizza);
    protected abstract int doDeletePizza(long pizzaId);
    protected abstract boolean pizzaExists(Pizza pizza);

    @Override
    public abstract List<Pizza> getPizzas();
    @Override
    public abstract List<Pizza> getBasicPizzas();
    @Override
    public abstract Optional<Pizza> getPizzaById(long pizzaId);

    public PizzaServiceBase(IUserService userService){
        this.userService = userService;
    }

    @Override
    public Optional<Pizza> savePizza(Pizza pizza, String userEmailAddress){
        checkIfPizzaIsValid(pizza);
        if(pizzaExists(pizza)){
            return Optional.empty();
        }
        User user;
        try{
            user = tryGetLoggedInUser(userEmailAddress);
        }catch (NoPermissionException e){
            throw new NoPermissionException("You have to be logged in to save pizza!");
        } catch (NotFoundException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        ifUserIsNotChefThrowException(user, "You have to have Chef role to save pizza!");
        return doSavePizza(pizza);
    }

    @Override
    public int updatePizzaWithoutAuthentication(Pizza pizza){
        checkIfPizzaIsValid(pizza);
        if(!pizzaExists(pizza)){
            return -1;
        }
        return doUpdatePizza(pizza);
    }

    @Override
    public int updatePizza(Pizza pizza, String userEmailAddress){
        checkIfPizzaIsValid(pizza);
        if(!pizzaExists(pizza)){
            return -1;
        }
        User user;
        try{
            user = tryGetLoggedInUser(userEmailAddress);
        }catch (NoPermissionException e){
            throw new NoPermissionException("You have to be logged in to update pizza!");
        } catch (NotFoundException e) {
            e.printStackTrace();
            return -1;
        }
        ifUserIsNotChefThrowException(user, "You have to have Chef role to update pizza!");
        return doUpdatePizza(pizza);
    }

    @Override
    public int deletePizza(long pizzaId, String userEmailAddress) {
        User user;
        try{
            user = tryGetLoggedInUser(userEmailAddress);
        }catch (NoPermissionException e){
            throw new NoPermissionException("You have to be logged in to delete pizza!");
        } catch (NotFoundException e) {
            e.printStackTrace();
            return -1;
        }
        ifUserIsNotChefThrowException(user, "You have to have Chef role to delete pizza!");
        return doDeletePizza(pizzaId);
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
    public BigDecimal calculatePrice(Pizza pizza) {
        checkIfPizzaIsValid(pizza);

        BigDecimal totalPrice = new BigDecimal(0);
        for (Ingredient ingredient: pizza.getIngredients()) {
            totalPrice = totalPrice.add(ingredient.getPrice());
        }

        return totalPrice;
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
        boolean pizzaPriceSmallerThan0 = pizza.getPrice().compareTo(BigDecimal.valueOf(0)) < 0;
        if(pizzaPriceSmallerThan0){
            throw new IllegalArgumentException("invalid pizza: invalid price");
        }
    }

    private static boolean areIngredientPricesValid(Pizza pizza) {
        for (Ingredient i: pizza.getIngredients()) {
            boolean ingredientPriceSmallerThan0 = i.getPrice().compareTo(BigDecimal.valueOf(0)) < 0;
            if(ingredientPriceSmallerThan0){
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

    private User tryGetLoggedInUser(String userEmailAddress) throws NotFoundException {
        Optional<User> user = userService.getLoggedInUser(userEmailAddress);
        return user.orElseThrow(() -> new NoPermissionException(""));
    }

    private void ifUserIsNotChefThrowException(User user, String exceptionMessage){
        if(!user.getRole().equals(Role.CHEF)){
            throw new NoPermissionException(exceptionMessage);
        }
    }
}
