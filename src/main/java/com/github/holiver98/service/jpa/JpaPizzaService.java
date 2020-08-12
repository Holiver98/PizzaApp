package com.github.holiver98.service.jpa;

import com.github.holiver98.dal.jpa.IIngredientRepository;
import com.github.holiver98.dal.jpa.IPizzaRepository;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.service.IUserService;
import com.github.holiver98.service.NotFoundException;
import com.github.holiver98.service.PizzaServiceBase;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class JpaPizzaService extends PizzaServiceBase {
    @Autowired
    private IIngredientRepository ingredientRepository;
    @Autowired
    private IPizzaRepository pizzaRepository;

    public JpaPizzaService(IUserService userService) {
        super(userService);
    }

    @Override
    public List<Pizza> getPizzas() {
        return pizzaRepository.findAll();
    }

    @Override
    public List<Pizza> getBasicPizzas() {
        return pizzaRepository.findByIsCustom(false);
    }

    @Override
    public Optional<Pizza> getPizzaById(long pizzaId){
        return pizzaRepository.findById(pizzaId);
    }

    @Override
    public Optional<Pizza> doSavePizza(Pizza pizza) {
        Pizza savedPizza;
        try{
            savedPizza = pizzaRepository.save(pizza);
        }catch (IllegalArgumentException e){
            return Optional.empty();
        }
        return Optional.of(savedPizza);
    }

    @Override
    protected int doUpdatePizza(Pizza pizza){
        pizzaRepository.save(pizza);
        return 1;
    }

    @Override
    protected int doDeletePizza(long pizzaId) {
        pizzaRepository.deleteById(pizzaId);
        return 1;
    }

    @Override
    protected boolean pizzaExists(Pizza pizza) {
        if(pizza.getId() == null){
            return false;
        }

        return pizzaRepository.existsById(pizza.getId());
    }
}