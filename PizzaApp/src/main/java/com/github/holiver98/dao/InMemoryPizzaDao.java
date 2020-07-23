package com.github.holiver98.dao;

import com.github.holiver98.database.InMemoryDatabase;
import com.github.holiver98.model.Pizza;

import java.util.List;

public class InMemoryPizzaDao implements IInMemoryPizzaDao {
    //TODO: implement InMemoryPizzaDao
    private InMemoryDatabase dbContext;

    public InMemoryPizzaDao(InMemoryDatabase context) {
        dbContext = context;
    }

    @Override
    public long savePizza(Pizza pizza) {
        System.out.println("Pizza saved: " + pizza);
        dbContext.pizzas.add(pizza);
        return 0;
    }

    @Override
    public List<Pizza> getPizzas() {
        return dbContext.pizzas;
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
