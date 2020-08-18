package com.github.holiver98.viewmodel;

import com.github.holiver98.dal.jpa.IPizzaRepository;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.ui.PizzaDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PizzaDetailsViewModel implements PizzaDetails.PizzaDetailsViewListener {
    @Autowired
    IPizzaRepository pizzaRepo;

    private Pizza pizza;

    @Override
    public void newPizzaSelected(Long pizzaId) {
        this.pizza = pizzaRepo.findById(pizzaId).orElse(null);
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
}
