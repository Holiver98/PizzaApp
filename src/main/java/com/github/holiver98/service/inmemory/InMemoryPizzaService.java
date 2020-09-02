package com.github.holiver98.service.inmemory;

import com.github.holiver98.dal.inmemory.IInMemoryIngredientDao;
import com.github.holiver98.dal.inmemory.IInMemoryPizzaDao;
import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.service.IUserService;
import com.github.holiver98.service.PizzaServiceBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO: implement methods
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
    public List<Pizza> getBasicNonLegacyPizzas() {
        return null; // TODO: implement
    }

    @Override
    public List<Pizza> getCustomPizzas() {
        return new ArrayList<Pizza>(); //TODO: implement method
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
    protected Pizza doSavePizza(Pizza pizza) {
        //return pizzaDao.savePizza(pizza);
        return null; //TODO: implement
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
    protected boolean pizzaExistsById(Pizza pizza) {
        Optional<Pizza> p = pizzaDao.getPizzaById(pizza.getId());
        if(p.isPresent()){
            return false;
        }else{
            return true;
        }
    }

    @Override
    protected boolean orderEntryExistsOnPizza(long pizzaId) {
        return false; //TODO: implement method
    }

    @Override
    protected Ingredient doSaveIngredient(Ingredient ingredient) {
        return null;
    }

    @Override
    protected int doUpdateIngredient(Ingredient ingredient) {
        return 0;
    }

    @Override
    protected int doDeleteIngredient(String ingredientName) {
        return 0;
    }

    @Override
    protected boolean isIngredientReferencedByAPizza(String ingredientName) {
        return false;
    }
}
