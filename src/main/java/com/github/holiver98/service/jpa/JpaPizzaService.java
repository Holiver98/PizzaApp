package com.github.holiver98.service.jpa;

import com.github.holiver98.dal.jpa.IIngredientRepository;
import com.github.holiver98.dal.jpa.IPizzaRepository;
import com.github.holiver98.dal.jpa.IRatingRepository;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.Rating;
import com.github.holiver98.service.IUserService;
import com.github.holiver98.service.NotFoundException;
import com.github.holiver98.service.PizzaServiceBase;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class JpaPizzaService extends PizzaServiceBase {
    @Autowired
    private IIngredientRepository ingredientRepository;
    @Autowired
    private IPizzaRepository pizzaRepository;

    public JpaPizzaService(IUserService userService) {
        super(userService);
    }

    @Override
    public long doSavePizza(Pizza pizza) {
        if(pizza.getId() != null){
            throw new IllegalArgumentException("pizza should not have an id, if you want to save it");
        }

        return pizzaRepository.save(pizza).getId();
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
    public Pizza getPizzaById(long pizzaId) throws NotFoundException {
        return pizzaRepository.findById(pizzaId)
                .orElseThrow(() -> new NotFoundException("pizza with id (" + pizzaId + ") not found"));
    }

    @Override
    public void doUpdatePizza(Pizza pizza){
        pizzaRepository.save(pizza);
    }

    @Override
    public void doDeletePizza(long pizzaId) {
        pizzaRepository.deleteById(pizzaId);
    }

    @Override
    protected boolean pizzaExists(Pizza pizza) {
        if(pizza.getId() == null){
            return false;
        }

        return pizzaRepository.existsById(pizza.getId());
    }
}
