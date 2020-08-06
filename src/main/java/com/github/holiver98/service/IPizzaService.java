package com.github.holiver98.service;

import com.github.holiver98.model.Pizza;

import java.util.List;

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
     * Saves the pizza into database.
     *
     * @param pizza The pizza to be saved.
     * @return The id the database generated for the pizza.
     * @throws NullPointerException if the pizza is null.
     * @throws IllegalArgumentException if the pizza is invalid.
     * @throws NoPermissionException if not logged in, or doesn't have Chef role.
     * @throws AlreadyExistsException if pizza already exists in the database.
     */
    long savePizza(Pizza pizza) throws AlreadyExistsException;

    /**
     * Gets all the pizzas from the database.
     *
     * @return A list of all the pizzas.
     */
    List<Pizza> getPizzas();

    /**
     * Gets all the pizzas from the database, that aren't a custom-made pizza.
     *
     * @return A list of the basic pizzas.
     */
    List<Pizza> getBasicPizzas();

    /**
     * Gets a pizza by it's id from the database.
     *
     * @param pizzaId The id of the pizza.
     * @return The pizza with the given id.
     * @throws NotFoundException - no pizza was found in the database with this id
     */
    Pizza getPizzaById(long pizzaId) throws NotFoundException;

    /**
     * Updates the pizza in the database that has the same id, as the pizza argument.
     * Requires authenticated user with Chef role.
     *
     * @param pizza The pizza to be updated.
     * @throws NullPointerException if the pizza is null.
     * @throws IllegalArgumentException if the pizza is invalid.
     * @throws NotFoundException if no pizza was found in the database with this id.
     * @throws NoPermissionException if not logged in, or doesn't have Chef role.
     */
    void updatePizza(Pizza pizza) throws NotFoundException;

    /**
     * Updates the pizza in the database that has the same id, as the pizza argument.
     * Does not require authentication.
     *
     * @param pizza The pizza to be updated.
     * @throws NullPointerException if the pizza is null.
     * @throws IllegalArgumentException if the pizza is invalid.
     * @throws NotFoundException if no pizza was found in the database with this id.
     */
    void updatePizzaWithoutAuthentication(Pizza pizza) throws NotFoundException;

    /**
     * Deletes the pizza from the database, if it exists.
     *
     * @param pizzaId The id of the pizza to be deleted.
     * @throws NoPermissionException if not logged in, or doesn't have Chef role.
     */
    void deletePizza(long pizzaId);
}
