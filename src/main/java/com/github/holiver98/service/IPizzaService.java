package com.github.holiver98.service;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.Pizza;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IPizzaService {
    /**
     * Calculates the price of the pizza.
     *
     * @param pizza The pizza, which price will be calculated.
     * @return The price of the pizza.
     * @throws NullPointerException if the pizza is null.
     * @throws IllegalArgumentException if the pizza is invalid.
     */
    BigDecimal calculatePrice(Pizza pizza);

    /**
     * Saves the pizza. Requires authenticated user with Chef role.
     *
     * @param pizza The pizza to be saved.
     * @param emailAddress the email address of the user who is saving the pizza.
     * @return The saved pizza with it's id set. If pizza already exists, null is returned.
     * @throws NullPointerException if the pizza or emailAddress is null.
     * @throws IllegalArgumentException if the pizza is invalid.
     * @throws NoPermissionException if doesn't have Chef role.
     * @throws NotRegisteredException if no user is registered with this emailAddress.
     */
    Optional<Pizza> savePizza(Pizza pizza, String emailAddress);

    /**
     * saves the pizza and returns the saved object.
     * If it is already saved, then it won't be saved, it will just return
     * the existing saved object.
     *
     * @param pizza the pizza to be saved.
     * @return the saved pizza object.
     */
    Pizza saveCustomPizzaWithoutAuthentication(Pizza pizza);

    List<Pizza> getPizzas();

    /**
     * Gets all the pizzas from the database, that aren't a custom-made pizza.
     *
     * @return A list of the basic pizzas.
     */
    List<Pizza> getBasicNonLegacyPizzas();

    List<Pizza> getCustomPizzas();

    /**
     * @return The pizza with the given id or null if it doesn't exist.
     */
    Optional<Pizza> getPizzaById(long pizzaId);

    /**
     *Updates the pizza, that has the same id, as the pizza argument, with the values of
     *the pizza argument.
     * Requires authenticated user with Chef role.
     *
     * @param pizza The pizza to be updated.
     * @param  emailAddress the email address of the user who is updating the pizza.
     * @return 1 - success, -1 if pizza doesn't exist.
     * @throws NullPointerException if the pizza or emailAddress is null.
     * @throws IllegalArgumentException if the pizza is invalid.
     * @throws NoPermissionException if doesn't have Chef role.
     * @throws NotRegisteredException if no user is registered with this emailAddress.
     */
    int updatePizza(Pizza pizza, String emailAddress);

    /**
     *Updates the pizza, that has the same id, as the pizza argument, with the values of
     *the pizza argument.
     * Does not require authentication.
     *
     * @param pizza The pizza to be updated.
     * @return 1 - success, -1 if pizza doesn't exist.
     * @throws NullPointerException if the pizza is null.
     * @throws IllegalArgumentException if the pizza is invalid.
     */
    int updatePizzaWithoutAuthentication(Pizza pizza);

    /**
     * Deletes the pizza, if it exists,
     * or if there are any order entries, that reference this pizza, then
     * it instead of removing it, the legacy flag will be set to true on this pizza.
     * Requires authenticated user with Chef role.
     *
     * @param pizzaId The id of the pizza to be deleted.
     * @param emailAddress The email address of the user, who is deleting the pizza.
     * @return 1 - success, -1 - pizza with this id doesn't exist.
     * @throws NullPointerException if emailAddress is null.
     * @throws NoPermissionException if doesn't have Chef role.
     * @throws NotRegisteredException if no user is registered with this emailAddress.
     */
    int deletePizza(long pizzaId, String emailAddress);

    List<Ingredient> getIngredients();

    /**
     * @return The ingredient with the given name or null if it doesn't exist.
     */
    Optional<Ingredient> getPizzaIngredientByName(String ingredientName);

    Optional<Ingredient> saveIngredient(Ingredient ingredient, String emailAddress);
    int updateIngredient(Ingredient ingredient, String emailAddress);
    int deleteIngredient(String ingredientName, String emailAddress);
}
