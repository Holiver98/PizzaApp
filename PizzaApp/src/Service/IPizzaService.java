package Service;

import Model.Pizza;

import java.util.List;

public interface IPizzaService {
    float calculatePrice();
    long savePizza(Pizza pizza);
    List<Pizza> getPizzas();
    List<Pizza> getBasicPizzas(); //non custom-made pizzas
    List<Pizza> getPizzaById(long pizzaId);
    void updatePizza(Pizza pizza);
    void deletePizza(Pizza pizza);
}
