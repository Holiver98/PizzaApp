package com.github.holiver98.service;

import com.github.holiver98.model.Pizza;

import java.util.List;

public interface IPizzaService {
    /**
     * Calculates the price of the pizza.
     *
     * @param pizza The pizza, which price will be calculated.
     * @return The price of the pizza, or -1, if there are no or invalid ingredients.
     */
    float calculatePrice(Pizza pizza);

    /**
     * Recalculates the rating average for the pizza, using fresh data from the com.github.holiver98.database.
     * It also updates the pizza in the com.github.holiver98.database with the new value.
     *
     * @param pizzaId The id of the pizza to update.
     * @return The average rating, or 0 if there are no ratings, or -1 if other problems arise (for example
     * the pizza is not in the com.github.holiver98.database).
     */
    float recalculateRatingAverage(long pizzaId);

    /**
     * Saves the pizza into com.github.holiver98.database.
     *
     * @param pizza The pizza to be saved.
     * @return The id the com.github.holiver98.database generated for the pizza, or -1, if the did not get saved.
     */
    long savePizza(Pizza pizza);

    /**
     * Gets all the pizzas from the com.github.holiver98.database.
     *
     * @return A list of all the pizzas.
     */
    List<Pizza> getPizzas();

    /**
     * Gets all the pizzas from the com.github.holiver98.database, that aren't a custom-made pizza.
     *
     * @return A list of the basic pizzas.
     */
    List<Pizza> getBasicPizzas(); //non custom-made pizzas

    /**
     * Gets a pizza by it's id from the com.github.holiver98.database.
     *
     * @param pizzaId The id of the pizza.
     * @return The pizza with the given id or null if it is not in the com.github.holiver98.database.
     */
    Pizza getPizzaById(long pizzaId);

    /**
     * Updates the pizza in the com.github.holiver98.database that has the same id, as the pizza argument.
     *
     * @param pizza The pizza to be updated.
     */
    void updatePizza(Pizza pizza);

    /**
     * Deletes the pizza from the com.github.holiver98.database, if it exists.
     *
     * @param pizzaId The id of the pizza to be deleted.
     */
    void deletePizza(long pizzaId);
}
