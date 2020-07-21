package dao;

import model.Pizza;

import java.util.List;

public interface IPizzaDao {
    /**
     * Saves the pizza into database.
     *
     * @param pizza The pizza to be saved.
     * @return The id the database generated for the pizza.
     */
    long savePizza(Pizza pizza);

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
    List<Pizza> getBasicPizzas(); //non custom-made pizzas

    /**
     * Gets a pizza by it's id from the database.
     *
     * @param pizzaId The id of the pizza.
     * @return The pizza with the given id or null if it is not in the database.
     */
    Pizza getPizzaById(long pizzaId);

    /**
     * Updates the pizza in the database, that has the same id, as the pizza argument.
     *
     * @param pizza The pizza to be updated.
     */
    void updatePizza(Pizza pizza);

    /**
     * Deletes the pizza from the database, if it exists.
     *
     * @param pizzaId The id of the pizza to be deleted.
     */
    void deletePizza(long pizzaId);
}
