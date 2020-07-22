package com.github.holiver98.dao;

import com.github.holiver98.model.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderDao {
    /**
     * Saves the order into com.github.holiver98.database.
     *
     * @param order The order to be saved.
     * @return The id the com.github.holiver98.database generated for the order, or -1 if the operation was
     * unsuccessful (for example the order already exists in the com.github.holiver98.database).
     */
    long saveOrder(Order order);

    /**
     * Gets all the orders from the com.github.holiver98.database.
     *
     * @return A list of all the orders.
     */
    List<Order> getOrders();

    /**
     * Gets all the orders of the given user from the com.github.holiver98.database.
     *
     * @param emailAddress The email address of the user.
     * @return A list of all the orders, that the user made.
     */
    List<Order> getOrdersOfUser(String emailAddress);

    /**
     * Gets an order by it's id from the com.github.holiver98.database.
     *
     * @param orderId The id of the order.
     * @return The order with the given id or null if it isn't in the com.github.holiver98.database.
     */
    Optional<Order> getOrderById(long orderId);

    /**
     * Updates the order in the com.github.holiver98.database, that has the same id, as the order argument.
     *
     * @param order The order to be updated.
     */
    void updateOrder(Order order);

    /**
     * Deleted the order from the com.github.holiver98.database, if it exists.
     *
     * @param orderId The id of the order to be deleted.
     */
    void deleteOrder(long orderId);
}
