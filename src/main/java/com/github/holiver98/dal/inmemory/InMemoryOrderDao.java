package com.github.holiver98.dal.inmemory;

import com.github.holiver98.model.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryOrderDao implements IInMemoryOrderDao {
    public List<Order> orders = new ArrayList<Order>();

    @Override
    public Optional<Order> saveOrder(Order order) {
        if(order == null){
            throw new NullPointerException("order is null");
        }

        Optional<Order> dbOrder = getOrderById(order.getId());
        if(dbOrder.isPresent()){
            //already in database
            return Optional.empty();
        }

        orders.add(order);
        long IndexInList = orders.indexOf(order);
        order.setId(IndexInList);
        return Optional.of(order);
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public List<Order> getOrdersOfUser(String emailAddress) {
        if(emailAddress == null){
            throw new NullPointerException("emailAddress is null");
        }
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
    public int updateOrder(Order order) {
        if(order == null){
            throw new NullPointerException("order is null");
        }

        Optional<Order> dbOrder = getOrderById(order.getId());
        if(dbOrder.isPresent()){
            updateOldOrderWithNew(dbOrder.get(), order);
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public int deleteOrder(long orderId) {
        Optional<Order> dbOrder = getOrderById(orderId);
        if(dbOrder.isPresent()){
            orders.remove(dbOrder.get());
            return 1;
        }else{
            return -1;
        }
    }

    private void updateOldOrderWithNew(Order oldOrder, Order newOrder){
        oldOrder.setDate(newOrder.getDate());
        oldOrder.setTotalPrice(newOrder.getTotalPrice());
        oldOrder.setUserEmailAddress(newOrder.getUserEmailAddress());
        oldOrder.setPizzas(newOrder.getPizzas());
        oldOrder.setId(newOrder.getId());
    }
}
