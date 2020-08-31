package com.github.holiver98.service.jpa;

import com.github.holiver98.dal.jpa.IIngredientRepository;
import com.github.holiver98.dal.jpa.IOrderRepository;
import com.github.holiver98.dal.jpa.IPizzaRepository;
import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.Order;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.service.IUserService;
import com.github.holiver98.service.PizzaServiceBase;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class JpaPizzaService extends PizzaServiceBase {
    @Autowired
    private IIngredientRepository ingredientRepository;
    @Autowired
    private IPizzaRepository pizzaRepository;
    @Autowired
    private IOrderRepository orderRepository;

    public JpaPizzaService(IUserService userService) {
        super(userService);
    }

    @Override
    public List<Pizza> getPizzas() {
        return pizzaRepository.findAll();
    }

    @Override
    public List<Pizza> getBasicNonLegacyPizzas() {
        return pizzaRepository.findByIsCustomAndIsLegacy(false, false);
    }

    @Override
    public List<Pizza> getCustomPizzas() {
        return pizzaRepository.findByIsCustom(true);
    }

    @Override
    public Optional<Pizza> getPizzaById(long pizzaId){
        return pizzaRepository.findById(pizzaId);
    }

    @Override
    public List<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }

    @Override
    public Optional<Ingredient> getPizzaIngredientByName(String ingredientName) {
        return ingredientRepository.findById(ingredientName);
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
    @Transactional //TODO: transactional csak public metódusokon működik??
    protected int doDeletePizza(long pizzaId) {
        pizzaRepository.deleteRatingsOfPizza(pizzaId);
        pizzaRepository.deleteById(pizzaId);
        return 1;
    }

    @Override
    protected boolean pizzaExistsById(Pizza pizza) {
        if(pizza.getId() == null){
            return false;
        }

        return pizzaRepository.existsById(pizza.getId());
    }

    @Override
    protected boolean orderEntryExistsOnPizza(long pizzaId) {
        List<Order> orders = orderRepository.findByPizzaId(pizzaId);
        return orders.size() > 0;
    }
}
