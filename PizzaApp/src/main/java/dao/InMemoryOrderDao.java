package dao;

import database.InMemoryDatabase;
import model.Order;

import java.util.List;

//TODO: implement class
public class InMemoryOrderDao implements IOrderDao{
    private InMemoryDatabase dbContext;

    public InMemoryOrderDao(InMemoryDatabase context) {
        dbContext = context;
    }

    @Override
    public long saveOrder(Order order) {
        return 0;
    }

    @Override
    public List<Order> getOrders() {
        return null;
    }

    @Override
    public List<Order> getOrdersOfUser(String emailAddress) {
        return null;
    }

    @Override
    public Order getOrderById(long orderId) {
        return null;
    }

    @Override
    public void updateOrder(Order order) {

    }

    @Override
    public void deleteOrder(long orderId) {

    }
}
