package com.github.holiver98.dao;

import com.github.holiver98.database.InMemoryDatabase;
import com.github.holiver98.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryOrderDao implements IOrderDao{
    private InMemoryDatabase dbContext;

    public InMemoryOrderDao(InMemoryDatabase context) {
        dbContext = context;
    }

    @Override
    public long saveOrder(Order order) {
        if(order == null){
            return -1;
        }

        Optional<Order> dbOrder = getOrderById(order.getId());
        if(dbOrder.isPresent()){
            //already in com.github.holiver98.database
            return -1;
        }

        dbContext.orders.add(order);
        return dbContext.orders.indexOf(order);
    }

    @Override
    public List<Order> getOrders() {
        return dbContext.orders;
    }

    @Override
    public List<Order> getOrdersOfUser(String emailAddress) {
        return dbContext.orders.stream()
                .filter(o -> o.getUserEmailAddress().equals(emailAddress))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Order> getOrderById(long orderId) {
        return dbContext.orders.stream()
                .filter(o -> o.getId() == orderId)
                .findFirst();
    }

    @Override
    public void updateOrder(Order order) {
        if(order == null){
            return;
        }

        Optional<Order> dbOrder = getOrderById(order.getId());
        dbOrder.ifPresent(o -> updateOldOrderWithNew(o, order));
    }

    @Override
    public void deleteOrder(long orderId) {
        Optional<Order> dbOrder = getOrderById(orderId);
        dbOrder.ifPresent(o -> dbContext.orders.remove(o));
    }

    private void updateOldOrderWithNew(Order oldOrder, Order newOrder){
        oldOrder.setDate(newOrder.getDate());
        oldOrder.setTotalPrice(newOrder.getTotalPrice());
        oldOrder.setUserEmailAddress(newOrder.getUserEmailAddress());
        oldOrder.setPizzas(newOrder.getPizzas());
        oldOrder.setId(newOrder.getId());
    }
}
