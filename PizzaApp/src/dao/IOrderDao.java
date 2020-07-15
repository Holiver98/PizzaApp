package dao;

import model.Order;

import java.util.List;

public interface IOrderDao {
    /**
     * Saves the order into database.
     *
     * @param order The order to be saved.
     * @return the id the database generated for the order.
     */
    long saveOrder(Order order);

    /**
     * Gets all the orders from the database.
     *
     * @return A list of all the orders.
     */
    List<Order> getOrders();

    /**
     * Gets all the orders of the given user from the database.
     *
     * @param emailAddress The email address of the user.
     * @return A list of all the orders, that the user made.
     */
    List<Order> getOrdersOfUser(String emailAddress);

    /**
     * Gets an order by it's id from the database.
     *
     * @param orderId The id of the order.
     * @return The order with the given id or null if it isn't in the database.
     */
    Order getOrderById(long orderId);

    /**
     * Updates the order in the database, that has the same id, as the order argument.
     *
     * @param order The order to be updated.
     */
    void updateOrder(Order order);

    /**
     * Deleted the order from the database, if it exists.
     *
     * @param order The order to be deleted.
     */
    void deleteOrder(Order order);
}
