package com.github.holiver98.dal.inmemory;

import com.github.holiver98.model.Pizza;
import java.util.ArrayList;
import java.util.List;

public class InMemoryPizzaDao implements IInMemoryPizzaDao {
    public List<Pizza> pizzas = new ArrayList<Pizza>();

    @Override
    public long savePizza(Pizza pizza) {
        System.out.println("Pizza saved: " + pizza);
        pizzas.add(pizza);
        return 0;
    }

    @Override
    public List<Pizza> getPizzas() {
        return pizzas;
    }

    @Override
    public List<Pizza> getBasicPizzas() {
        return null;
    }

    @Override
    public Pizza getPizzaById(long pizzaId) {
        return null;
    }

    @Override
    public void updatePizza(Pizza pizza) {

    }

    @Override
    public void deletePizza(long pizzaId) {

    }
}
