package com.github.holiver98.service.inmemory;

import com.github.holiver98.dal.inmemory.IInMemoryIngredientDao;
import com.github.holiver98.dal.inmemory.IInMemoryPizzaDao;
import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.service.IUserService;
import com.github.holiver98.service.NotFoundException;
import com.github.holiver98.service.PizzaServiceBase;

import java.util.List;
import java.util.Optional;

public class InMemoryPizzaService extends PizzaServiceBase {

    private IInMemoryIngredientDao ingredientDao;
    private IInMemoryPizzaDao pizzaDao;

    public InMemoryPizzaService(IInMemoryPizzaDao pizzaDao, IInMemoryIngredientDao ingredientDao
            , IUserService userService){
        super(userService);
        this.pizzaDao = pizzaDao;
        this.ingredientDao = ingredientDao;
    }

    @Override
    public List<Pizza> getPizzas() {
        return pizzaDao.getPizzas();
    }

    @Override
    public List<Pizza> getBasicPizzas() {
        return pizzaDao.getBasicPizzas();
    }

    @Override
    public Optional<Pizza> getPizzaById(long pizzaId){
        if(pizzaId < 0){
            return Optional.empty();
        }
        return pizzaDao.getPizzaById(pizzaId);
    }

    @Override
    public List<Ingredient> getIngredients() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Optional<Ingredient> getPizzaIngredientByName(String ingredientName) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    protected Optional<Pizza> doSavePizza(Pizza pizza) {
        return pizzaDao.savePizza(pizza);
    }

    @Override
    protected int doUpdatePizza(Pizza pizza) {
        return pizzaDao.updatePizza(pizza);
    }

    @Override
    protected int doDeletePizza(long pizzaId) {
        if(pizzaId < 0){
            return -1;
        }

        return pizzaDao.deletePizza(pizzaId);
    }

    @Override
    protected boolean pizzaExists(Pizza pizza) {
        Optional<Pizza> p = pizzaDao.getPizzaById(pizza.getId());
        if(p.isPresent()){
            return false;
        }else{
            return true;
        }
    }
}
