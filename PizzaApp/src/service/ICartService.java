package service;

import model.Pizza;

public interface ICartService {
    void addPizzaToCart(Pizza pizza);
    void removePizzaFromCart(Pizza pizza);
    void placeOrder();
}
