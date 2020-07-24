package com.github.holiver98.service.jpa;

import com.github.holiver98.dal.jpa.IIngredientRepository;
import com.github.holiver98.dal.jpa.IPizzaRepository;
import com.github.holiver98.dal.jpa.IRatingRepository;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.Rating;
import com.github.holiver98.service.PizzaServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class JpaPizzaService extends PizzaServiceBase {
    @Autowired
    private IIngredientRepository ingredientRepository;
    @Autowired
    private IPizzaRepository pizzaRepository;
    @Autowired
    private IRatingRepository ratingRepository;

    @Override
    protected List<Rating> getRatingsOfPizza(long pizzaId) {
        return ratingRepository.findByPizzaId(pizzaId);
    }

    @Override
    public long savePizza(Pizza pizza) {
        if(!isValidPizza(pizza)){
            return -1;
        }

        if(pizzaRepository.existsById(pizza.getId())){
            System.out.println("Pizza already exists!");
            return -1;
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
    public Pizza getPizzaById(long pizzaId) {
        if(pizzaId < 0){
            return null;
        }else{
            return pizzaRepository.findById(pizzaId).orElse(null);
        }
    }

    @Override
    public void updatePizza(Pizza pizza) {
        if(!isValidPizza(pizza)){
            return;
        }

        if(!pizzaRepository.existsById(pizza.getId())){
            System.out.println("Pizza doesn't exist!");
            return;
        }

        pizzaRepository.save(pizza);
    }

    @Override
    public void deletePizza(long pizzaId) {
        if(pizzaId < 0){
            return;
        }

        pizzaRepository.deleteById(pizzaId);
    }
}
