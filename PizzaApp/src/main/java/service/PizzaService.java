package service;

import dao.IIngredientDao;
import dao.IPizzaDao;
import model.Ingredient;
import model.Pizza;

import java.math.BigDecimal;
import java.util.List;

public class PizzaService implements IPizzaService {

    private IIngredientDao ingredientDao;
    private IPizzaDao pizzaDao;

    public PizzaService(IPizzaDao pizzaDao, IIngredientDao ingredientDao){
        this.pizzaDao = pizzaDao;
        this.ingredientDao = ingredientDao;
    }

    @Override
    public float calculatePrice(Pizza pizza) {
        if(pizza == null){
            return -1f;
        }

        if(pizza.getIngredients() == null){
            System.out.println("Ingredients list not initialized!");
            return -1f;
        }

        if(pizza.getIngredients().size() == 0){
            System.out.println("There are no ingredients assigned to this pizza!");
            return -1f;
        }

        if(!arePricesValid(pizza)){
            System.out.println("Invalid ingredient prices!");
            return -1f;
        }

        BigDecimal totalPrice = new BigDecimal(0);
        for (Ingredient i: pizza.getIngredients()) {
            BigDecimal ingredientPrice = new BigDecimal(Float.toString(i.getPrice()));
            totalPrice = totalPrice.add(ingredientPrice);
        }

        return totalPrice.floatValue();
    }

    private boolean arePricesValid(Pizza pizza) {
        for (Ingredient i: pizza.getIngredients()) {
            if(i.getPrice() < 0f){
                return false;
            }
        }

        return true;
    }

    @Override
    public float recalculateRatingAverage(long pizzaId) {
        return 0;
    }

    @Override
    public long savePizza(Pizza pizza) {
        return 0;
    }

    @Override
    public List<Pizza> getPizzas() {
        return null;
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
    public void deletePizza(Pizza pizza) {

    }
}
