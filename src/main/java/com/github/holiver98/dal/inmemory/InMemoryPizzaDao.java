package com.github.holiver98.dal.inmemory;

import com.github.holiver98.model.Pizza;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryPizzaDao implements IInMemoryPizzaDao {
    public List<Pizza> pizzas = new ArrayList<Pizza>();

    @Override
    public Optional<Pizza> savePizza(Pizza pizza) {
        if(pizza == null){
            throw new NullPointerException("pizza is null");
        }

        Optional<Pizza> dbpizza = getPizzaById(pizza.getId());
        if(dbpizza.isPresent()){
            //already in database
            return Optional.empty();
        }

        pizzas.add(pizza);
        long IndexInList = pizzas.indexOf(pizza);
        pizza.setId(IndexInList);
        return Optional.of(pizza);
    }

    @Override
    public List<Pizza> getPizzas() {
        return pizzas;
    }

    @Override
    public List<Pizza> getBasicPizzas() {
        return pizzas.stream()
                .filter(p -> !p.isCustom())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Pizza> getPizzaById(long pizzaId) {
        return pizzas.stream()
                .filter(p -> p.getId() == pizzaId)
                .findFirst();
    }

    @Override
    public int updatePizza(Pizza pizza) {
        if(pizza == null){
            throw new NullPointerException("pizza is null");
        }

        Optional<Pizza> dbPizza = getPizzaById(pizza.getId());
        if(dbPizza.isPresent()){
            updateOldPizzaWithNew(dbPizza.get(), pizza);
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public int deletePizza(long pizzaId) {
        Optional<Pizza> dbPizza = getPizzaById(pizzaId);
        if(dbPizza.isPresent()){
            pizzas.remove(dbPizza.get());
            return 1;
        }else{
            return -1;
        }
    }

    private void updateOldPizzaWithNew(Pizza oldPizza, Pizza newPizza){
        oldPizza.setSize(newPizza.getSize());
        oldPizza.setName(newPizza.getName());
        oldPizza.setIngredients(newPizza.getIngredients());
        oldPizza.setPrice(newPizza.getPrice());
        oldPizza.setId(newPizza.getId());
        oldPizza.setRatingAverage(newPizza.getRatingAverage());
        oldPizza.setCustom(newPizza.isCustom());
    }
}
