package com.github.holiver98.service;

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
     * @param userEmailAddress The email address of the user, who wants to save the pizza.
     * @return The saved pizza with it's id set. If pizza already exists or
     * userEmailAddress is not registered, null is returned.
     * @throws NullPointerException if the pizza is null.
     * @throws IllegalArgumentException if the pizza is invalid.
     * @throws NoPermissionException if not logged in, or doesn't have Chef role.
     */
    Optional<Pizza> savePizza(Pizza pizza, String userEmailAddress);

    List<Pizza> getPizzas();

    /**
     * Gets all the pizzas from the database, that aren't a custom-made pizza.
     *
     * @return A list of the basic pizzas.
     */
    List<Pizza> getBasicPizzas();

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
     * @param userEmailAddress The email address of the user, who wants to update the pizza.
     * @return 1 - success, -1 if pizza doesn't exist or userEmailAddress is not registered.
     * @throws NullPointerException if the pizza is null.
     * @throws IllegalArgumentException if the pizza is invalid.
     * @throws NoPermissionException if not logged in, or doesn't have Chef role.
     */
    int updatePizza(Pizza pizza, String userEmailAddress);

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
     * Deletes the pizza, if it exists.
     * Requires authenticated user with Chef role.
     *
     * @param pizzaId The id of the pizza to be deleted.
     * @param userEmailAddress The email address of the user, who wants to delete the pizza.
     * @return 1 - success, -1 - pizza with this id doesn't exist or userEmailAddress is not registered.
     * @throws NoPermissionException if not logged in, or doesn't have Chef role.
     */
    int deletePizza(long pizzaId, String userEmailAddress);
}
