package model;

import java.util.Date;
import java.util.List;

public class Order {
    private long id;
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
}
