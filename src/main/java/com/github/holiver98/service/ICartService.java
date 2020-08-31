package com.github.holiver98.service;

import com.github.holiver98.model.Pizza;

import javax.mail.MessagingException;
import java.util.List;

public interface ICartService {
    /**
     * Returns the cart's content.
     *
     * @return A list of pizzas.
     */
    List<Pizza> getCartContent();

    /**
     * Adds the given pizza to the cart.
     *
     * @param pizza The pizza to be added to the cart.
     * @throws CartIsFullException if the cart is full and no more pizzas can be added.
     * @throws NullPointerException if the pizza is null.
     * @throws IllegalArgumentException if the pizza is not valid.
     */
    void addPizzaToCart(Pizza pizza) throws CartIsFullException;

    /**
     * Removes the given pizza from the cart.
     *
     * @param pizza The pizza to be removed from the cart.
     * @throws NullPointerException if the pizza is null.
     */
    void removePizzaFromCart(Pizza pizza);

    /**
     * Places an order with the current content of the cart, logs the order and
     * notifies the user with an email containing the order information.
     *
     * @param emailAddress the user's email address, who is placing the order.
     *
     * @throws CartIsEmptyException if the cart is empty.
     * @throws MessagingException if an error occurred with the order confirmation email.
     * @throws NullPointerException if emailAddress is null.
     * @throws NotRegisteredException if no user is registered with this emailAddress.
     */
    void placeOrder(String emailAddress) throws CartIsEmptyException, MessagingException;

    void clearContent();
}
