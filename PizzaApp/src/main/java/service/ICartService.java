package service;

import model.Pizza;

import java.util.List;

public interface ICartService {
    /**
     * Returns the cart's content.
     *
     * @return A list of pizzas.
     */
    List<Pizza> getCartContent();

    /**
     * Adds the given pizza to the cart, if it is not already full.
     *
     * @param pizza The pizza to be added to the cart.
     */
    void addPizzaToCart(Pizza pizza);

    /**
     * Removes the given pizza from the cart, if it is in the cart.
     *
     * @param pizza The pizza to be removed from the cart.
     */
    void removePizzaFromCart(Pizza pizza);

    /**
     * Places an order with the current content of the cart.
     * If the order can be placed, then it logs the order and
     * notifies the user with an email containing the order information.
     * Only logged in users may place an order.
     */
    void placeOrder();
}
