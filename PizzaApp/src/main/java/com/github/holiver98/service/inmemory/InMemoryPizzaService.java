package com.github.holiver98.service.inmemory;

import com.github.holiver98.dal.inmemory.IInMemoryIngredientDao;
import com.github.holiver98.dal.inmemory.IInMemoryPizzaDao;
import com.github.holiver98.dal.inmemory.IInMemoryRatingDao;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.Rating;
import com.github.holiver98.service.NotFoundException;
import com.github.holiver98.service.PizzaServiceBase;

import java.util.List;

public class InMemoryPizzaService extends PizzaServiceBase {

    private IInMemoryIngredientDao ingredientDao;
    private IInMemoryPizzaDao pizzaDao;
    private IInMemoryRatingDao ratingDao;

    public InMemoryPizzaService(IInMemoryPizzaDao pizzaDao, IInMemoryIngredientDao ingredientDao, IInMemoryRatingDao ratingDao){
        this.pizzaDao = pizzaDao;
        this.ingredientDao = ingredientDao;
        this.ratingDao = ratingDao;
    }

    @Override
    protected List<Rating> getRatingsOfPizza(long pizzaId) {
        return ratingDao.getRatingsOfPizza(pizzaId);
    }

    @Override
    public long savePizza(Pizza pizza) {
        if(!isValidPizza(pizza)){
            throw new IllegalArgumentException("invalid pizza");
        }

        return pizzaDao.savePizza(pizza);
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
    public Pizza getPizzaById(long pizzaId) throws NotFoundException {
        if(pizzaId < 0){
            return null;
        }else{
            Pizza pizza = pizzaDao.getPizzaById(pizzaId);
            if(pizza == null){
                throw new NotFoundException("pizza not found with id: " + pizzaId);
            }else{
                return pizza;
            }
        }
    }

    @Override
    public void updatePizza(Pizza pizza) {
        if(!isValidPizza(pizza)){
            return;
        }

        pizzaDao.updatePizza(pizza);
    }

    @Override
    public void deletePizza(long pizzaId) {
        if(pizzaId < 0){
            return;
        }

        pizzaDao.deletePizza(pizzaId);
    }
}
