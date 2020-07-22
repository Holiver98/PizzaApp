package com.github.holiver98.dao;

import com.github.holiver98.model.Pizza;

import java.util.List;

public interface IPizzaDao {
    /**
     * Saves the pizza into com.github.holiver98.database.
     *
     * @param pizza The pizza to be saved.
     * @return The id the com.github.holiver98.database generated for the pizza.
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
     * Updates the pizza in the com.github.holiver98.database, that has the same id, as the pizza argument.
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
