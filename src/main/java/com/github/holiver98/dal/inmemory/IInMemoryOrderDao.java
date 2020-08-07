package com.github.holiver98.dal.inmemory;

import com.github.holiver98.model.Order;

import java.util.List;
import java.util.Optional;

public interface IInMemoryOrderDao {
    /**
     * @return The saved order with it's id set. If order already exists, null is returned.
     * @throws NullPointerException if order is null.
     */
    Optional<Order> saveOrder(Order order);

    List<Order> getOrders();

    /**
     * Gets all the orders of the given user.
     *
     * @param emailAddress The email address of the user.
     * @return A list of all the orders, that the user made.
     * @throws NullPointerException if emailAddress is null.
     */
    List<Order> getOrdersOfUser(String emailAddress);

    /**
     * @return The order with the given id or null if it doesn't exist.
     */
    Optional<Order> getOrderById(long orderId);

    /**
     * Updates the order, that has the same id, as the order argument, with the values of
     * the order argument.
     *
     * @return 1 - success, -1 - order doesn't exist.
     * @throws NullPointerException if order is null.
     */
    int updateOrder(Order order);

    /**
     * Deletes the order with the given id, if it exists.
     *
     * @param orderId The id of the order to be deleted.
     * @return 1 - success, -1 - order with this id doesn't exist
     */
    int deleteOrder(long orderId);
}
