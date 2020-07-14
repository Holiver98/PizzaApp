package DAO;

import Model.Order;

import java.util.List;

public interface IOrderDao {
    long saveOrder(Order order);
    List<Order> getOrders();
    List<Order> getOrdersOfUser(String emailAddress);
    Order getOrderById(long orderId);
    void updateOrder(Order order);
    void deleteOrder(Order order);
}
