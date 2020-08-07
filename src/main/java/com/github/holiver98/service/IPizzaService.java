package com.github.holiver98.service;

import com.github.holiver98.model.Pizza;

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
    float calculatePrice(Pizza pizza);

    /**
     * @param pizza The pizza to be saved.
     * @return The saved pizza with it's id set. If pizza already exists, null is returned.
     * @throws NullPointerException if the pizza is null.
     * @throws IllegalArgumentException if the pizza is invalid.
     * @throws NoPermissionException if not logged in, or doesn't have Chef role.
     */
    Optional<Pizza> savePizza(Pizza pizza);

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
     * @return 1 - success, -1 if pizza doesn't exist.
     * @throws NullPointerException if the pizza is null.
     * @throws IllegalArgumentException if the pizza is invalid.
     * @throws NoPermissionException if not logged in, or doesn't have Chef role.
     */
    int updatePizza(Pizza pizza);

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
     * Deletes the pizza from the database, if it exists.
     *
     * @param pizzaId The id of the pizza to be deleted.
     * @return 1 - success, -1 - pizza with this id doesn't exist
     * @throws NoPermissionException if not logged in, or doesn't have Chef role.
     */
    int deletePizza(long pizzaId);
}
