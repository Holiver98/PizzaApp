package dao;

import database.InMemoryDatabase;
import model.Pizza;

import java.util.List;

public class InMemoryPizzaDao implements IPizzaDao {
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
