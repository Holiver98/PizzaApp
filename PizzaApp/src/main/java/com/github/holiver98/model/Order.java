package com.github.holiver98.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Order {
    private long id = -1;
    private String userEmailAddress;
    private List<Pizza> pizzas;
    private Date date;
    private float totalPrice;

    public long getId() {
        return id;
    }

    public String getUserEmailAddress() {
        return userEmailAddress;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public Date getDate() {
        return date;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress = userEmailAddress;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userEmailAddress='" + userEmailAddress + '\'' +
                ", pizzas=" + pizzas +
                ", date=" + date +
                ", totalPrice=" + totalPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return id == order.id &&
                Float.compare(order.totalPrice, totalPrice) == 0 &&
                userEmailAddress.equals(order.userEmailAddress) &&
                pizzas.equals(order.pizzas) &&
                date.equals(order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userEmailAddress, pizzas, date, totalPrice);
    }
}
