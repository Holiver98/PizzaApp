package Service;

import Model.Pizza;

public interface ICartService {
    void addPizzaToCart(Pizza pizza);
    void removePizzaFromCart(Pizza pizza);
    void placeOrder();
}
