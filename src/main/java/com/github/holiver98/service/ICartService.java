package com.github.holiver98.service;

import com.github.holiver98.model.Pizza;

import javax.mail.MessagingException;
import java.util.List;

public interface ICartService {
    /**
     * Places an order with the current content of the cart, logs the order and
     * notifies the user with an email containing the order information.
     *
     * @param cartContent the list of pizzas, that we want to order.
     * @param emailAddress the user's email address, who is placing the order.
     *
     * @throws CartIsEmptyException if cartContent is empty.
     * @throws MessagingException if an error occurred with the order confirmation email.
     * @throws NullPointerException if emailAddress is null.
     * @throws NotRegisteredException if no user is registered with this emailAddress.
     */
    void placeOrder(String emailAddress, List<Pizza> cartContent) throws CartIsEmptyException, MessagingException;
}
