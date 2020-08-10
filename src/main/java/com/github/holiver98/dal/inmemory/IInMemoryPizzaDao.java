package com.github.holiver98.dal.inmemory;

import com.github.holiver98.model.Pizza;

import java.util.List;
import java.util.Optional;

public interface IInMemoryPizzaDao {
    /**
     * @return The saved pizza with it's id set. If pizza already exists, null is returned.
     * @throws NullPointerException if pizza is null.
     */
    Optional<Pizza> savePizza(Pizza pizza);

    List<Pizza> getPizzas();

    /**
     * Gets all the pizzas, that aren't a custom-made pizza.
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
     *
     *@return 1 - success, -1 - pizza doesn't exist.
     * @throws NullPointerException if pizza is null.
     */
    int updatePizza(Pizza pizza);

    /**
     *Deletes the pizza with the given id, if it exists.
     *
     *@param pizzaId The id of the order to be deleted.
     *@return 1 - success, -1 - pizza with this id doesn't exist
     */
    int deletePizza(long pizzaId);
}
