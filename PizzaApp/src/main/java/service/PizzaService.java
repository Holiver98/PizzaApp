package service;

import dao.IIngredientDao;
import dao.IPizzaDao;
import dao.IRatingDao;
import model.Ingredient;
import model.Pizza;
import model.Rating;

import java.math.BigDecimal;
import java.util.List;

public class PizzaService implements IPizzaService {

    private IIngredientDao ingredientDao;
    private IPizzaDao pizzaDao;
    private IRatingDao ratingDao;

    public PizzaService(IPizzaDao pizzaDao, IIngredientDao ingredientDao, IRatingDao ratingDao){
        this.pizzaDao = pizzaDao;
        this.ingredientDao = ingredientDao;
        this.ratingDao = ratingDao;
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

    @Override
    public float recalculateRatingAverage(long pizzaId) {
        Pizza pizzaToUpdate = pizzaDao.getPizzaById(pizzaId);
        if(pizzaToUpdate == null){
            System.out.println("Pizza with id("+ pizzaId +") was not found in the database!");
            return -1;
        }

        List<Rating> ratings = ratingDao.getRatingsOfPizza(pizzaId);
        int ratingSumm = 0;
        for (Rating rating : ratings) {
            ratingSumm += rating.getRating();
        }

        float newRatingAverage;
        if(ratings.size() == 0){
            return 0;
        }else{
            newRatingAverage = (float)ratingSumm / (float)ratings.size();
        }

        pizzaToUpdate.setRatingAverage(newRatingAverage);
        pizzaDao.updatePizza(pizzaToUpdate);

        return newRatingAverage;
    }

    @Override
    public long savePizza(Pizza pizza) {
        if(!isValidPizza(pizza)){
            return -1;
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
    public Pizza getPizzaById(long pizzaId) {
        if(pizzaId < 0){
            return null;
        }else{
            return pizzaDao.getPizzaById(pizzaId);
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

    private boolean arePricesValid(Pizza pizza) {
        for (Ingredient i: pizza.getIngredients()) {
            if(i.getPrice() < 0f){
                return false;
            }
        }

        return true;
    }

    private boolean isValidPizza(Pizza pizza){
        if(pizza == null){
            return false;
        }else if(pizza.getId() < 0){
            return false;
        }else if(!arePricesValid(pizza)){
            return false;
        }

        return true;
    }
}
