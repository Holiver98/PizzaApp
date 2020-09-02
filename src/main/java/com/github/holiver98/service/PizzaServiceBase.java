package com.github.holiver98.service;

import com.github.holiver98.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class PizzaServiceBase implements IPizzaService {
    protected IUserService userService;
    protected static final int minToppings = 1;
    protected static final int maxToppings = 5;
    protected static final int maxBaseSauce = 1;
    protected static final int minBaseSauce = 1;

    protected abstract Pizza doSavePizza(Pizza pizza);
    protected abstract int doUpdatePizza(Pizza pizza);
    protected abstract int doDeletePizza(long pizzaId);
    protected abstract boolean pizzaExistsById(Pizza pizza);
    protected abstract boolean orderEntryExistsOnPizza(long pizzaId);

    protected abstract Ingredient doSaveIngredient(Ingredient ingredient);
    protected abstract int doUpdateIngredient(Ingredient ingredient);
    protected abstract int doDeleteIngredient(String ingredientName);
    protected abstract boolean isIngredientReferencedByAPizza(String ingredientName);

    @Override
    public abstract List<Pizza> getPizzas();
    @Override
    public abstract List<Pizza> getBasicNonLegacyPizzas();
    @Override
    public abstract List<Pizza> getCustomPizzas();
    @Override
    public abstract Optional<Pizza> getPizzaById(long pizzaId);
    @Override
    public abstract List<Ingredient> getIngredients();
    @Override
    public abstract Optional<Ingredient> getPizzaIngredientByName(String ingredientName);

    public PizzaServiceBase(IUserService userService){
        this.userService = userService;
    }

    @Override
    public Optional<Ingredient> saveIngredient(Ingredient ingredient, String emailAddress){
        if(emailAddress == null){
            throw new NullPointerException("emailAddress is null");
        }
        checkIfIngredientIsValid(ingredient);

        Optional<Ingredient> existingIngredient = getPizzaIngredientByName(ingredient.getName());
        if(existingIngredient.isPresent()){
            return Optional.empty();
        }

        User user = tryGetUser(emailAddress);
        ifUserIsNotChefThrowException(user, "You have to have Chef role to save ingredient!");
        return Optional.of(doSaveIngredient(ingredient));
    }

    @Override
    public int updateIngredient(Ingredient ingredient, String emailAddress){
        if(emailAddress == null){
            throw new NullPointerException("emailAddress is null");
        }
        checkIfIngredientIsValid(ingredient);

        User user = tryGetUser(emailAddress);
        ifUserIsNotChefThrowException(user, "You have to have Chef role to save ingredient!");
        return doUpdateIngredient(ingredient);
    }

    @Override
    public int deleteIngredient(String ingredientName, String emailAddress){
        if(emailAddress == null){
            throw new NullPointerException("emailAddress is null");
        }

        User user = tryGetUser(emailAddress);
        ifUserIsNotChefThrowException(user, "You have to have Chef role to save ingredient!");

        if(isIngredientReferencedByAPizza(ingredientName)){
            return -1;
        }

        return doDeleteIngredient(ingredientName);
    }

    @Override
    public Optional<Pizza> savePizza(Pizza pizza, String emailAddress){
        if(emailAddress == null){
            throw new NullPointerException("emailAddress is null");
        }
        checkIfPizzaIsValid(pizza);
        if(pizzaExistsById(pizza)){
            return Optional.empty();
        }
        User user = tryGetUser(emailAddress);
        ifUserIsNotChefThrowException(user, "You have to have Chef role to save pizza!");
        return Optional.of(doSavePizza(pizza));
    }

    @Override
    public Pizza saveCustomPizzaWithoutAuthentication(Pizza pizza){
        checkIfPizzaIsValid(pizza);

        Optional<Pizza> existingSavedPizza = getPizzaIfExists(pizza);
        if(existingSavedPizza.isPresent()){
            //return the existing saved object
            return existingSavedPizza.get();
        }

        //if it doesn't exist, we save it and return the saved object
        return doSavePizza(pizza);
    }

    private Optional<Pizza> getPizzaIfExists(Pizza pizza) {
        List<Pizza> customPizzas = getCustomPizzas();

        for (Pizza p : customPizzas) {
            Long pizzaId = new Long(p.getId());
            p.setId(null);
            /*
            The pizza we want to compare doesn't have an id, but the ones in the list do have id's, so the
            equals() would always return false. This is why we set the id to null... we don't care about
            the ids in this comparison.
            */
            if(p.equals(pizza)){
                //here we set it back to the old value, because we want to return the object
                //with it's id
                p.setId(pizzaId);
                return Optional.of(p);
            }
        }

        return Optional.empty();
    }

    @Override
    public int updatePizzaWithoutAuthentication(Pizza pizza){
        checkIfPizzaIsValid(pizza);
        if(!pizzaExistsById(pizza)){
            return -1;
        }
        return doUpdatePizza(pizza);
    }

    @Override
    public int updatePizza(Pizza pizza, String emailAddress){
        if(emailAddress == null){
            throw new NullPointerException("emailAddress is null");
        }
        checkIfPizzaIsValid(pizza);
        if(!pizzaExistsById(pizza)){
            return -1;
        }
        User user = tryGetUser(emailAddress);
        ifUserIsNotChefThrowException(user, "You have to have Chef role to update pizza!");
        return doUpdatePizza(pizza);
    }

    @Override
    public int deletePizza(long pizzaId, String emailAddress) {
        if(emailAddress == null){
            throw new NullPointerException("emailAddress is null");
        }
        User user = tryGetUser(emailAddress);
        ifUserIsNotChefThrowException(user, "You have to have Chef role to delete pizza!");

        if(orderEntryExistsOnPizza(pizzaId)){
            Pizza pizza = getPizzaById(pizzaId)
                    .orElseThrow(() -> new NullPointerException("pizza not found with id: " + pizzaId));
            pizza.setLegacy(true);
            updatePizza(pizza, emailAddress);
            return 1;
        }

        return doDeletePizza(pizzaId);
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
        checkIfHasValidNumberOfToppings(pizza);
        checkIfHasValidIngredientPrices(pizza);
        checkIfPizzaPriceIsNotNegative(pizza);
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

    private static void checkIfHasValidNumberOfToppings(Pizza pizza) {
        List<Ingredient> toppings = pizza.getIngredients().stream()
                .filter(i -> i.getType().equals(IngredientType.PIZZA_TOPPING))
                .collect(Collectors.toList());
        boolean hasInvalidNumberOfToppings = toppings.size() < minToppings ||
                toppings.size() > maxToppings;
        if(hasInvalidNumberOfToppings){
            throw new IllegalArgumentException("invalid pizza: invalid number of toppings");
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

    private User tryGetUser(String emailAddress) {
        Optional<User> user = userService.getUser(emailAddress);
        return user.orElseThrow(() -> new NotRegisteredException("No user is registered with this email address: " + emailAddress));
    }

    private void ifUserIsNotChefThrowException(User user, String exceptionMessage){
        if(!user.getRole().equals(Role.CHEF)){
            throw new NoPermissionException(exceptionMessage);
        }
    }

    private void checkIfIngredientIsValid(Ingredient ingredient) {
        if(ingredient == null){
            throw new NullPointerException("ingredient is null");
        }else if(ingredient.getType() == null){
            throw new NullPointerException("ingredient type is null");
        }else if(ingredient.getName() == null){
            throw new NullPointerException("ingredient name is null");
        }else if(ingredient.getName().length() < 1){
            throw new IllegalArgumentException("ingredient name too short: " + ingredient.getName().length());
        }
    }
}
