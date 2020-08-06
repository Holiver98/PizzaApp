package com.github.holiver98.dal.inmemory;

import com.github.holiver98.model.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryOrderDao implements IInMemoryOrderDao {
    public List<Order> orders = new ArrayList<Order>();

    @Override
    public long saveOrder(Order order) {
        if(order == null){
            return -1;
        }

        Optional<Order> dbOrder = getOrderById(order.getId());
        if(dbOrder.isPresent()){
            //already in database
            return -1;
        }

        orders.add(order);
        return orders.indexOf(order);
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public List<Order> getOrdersOfUser(String emailAddress) {
        return orders.stream()
                .filter(o -> o.getUserEmailAddress().equals(emailAddress))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Order> getOrderById(long orderId) {
        return orders.stream()
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
        dbOrder.ifPresent(o -> orders.remove(o));
    }

    private void updateOldOrderWithNew(Order oldOrder, Order newOrder){
        oldOrder.setDate(newOrder.getDate());
        oldOrder.setTotalPrice(newOrder.getTotalPrice());
        oldOrder.setUserEmailAddress(newOrder.getUserEmailAddress());
        oldOrder.setPizzas(newOrder.getPizzas());
        oldOrder.setId(newOrder.getId());
    }
}
