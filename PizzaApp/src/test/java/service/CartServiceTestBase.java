package service;

import model.Ingredient;
import model.IngredientType;
import model.Pizza;
import model.PizzaSize;

import java.util.HashSet;
import java.util.Set;

public class CartServiceTestBase {
    protected Pizza createValidPizza(String name){
        Pizza pizza = new Pizza();
        pizza.setName(name);
        pizza.setSize(PizzaSize.NORMAL);
        pizza.setPrice(20.5f);
        pizza.setCustom(false);

        Set<Ingredient> ingredients = new HashSet<Ingredient>();
        Ingredient tomatoSauce = new Ingredient();
        tomatoSauce.setName("tomato sauce");
        tomatoSauce.setPrice(2.1f);
        tomatoSauce.setType(IngredientType.PIZZA_BASESAUCE);

        Ingredient cheese = new Ingredient();
        cheese.setName("mozarella");
        cheese.setPrice(3.3f);
        cheese.setType(IngredientType.PIZZA_TOPPING);

        ingredients.add(tomatoSauce);
        ingredients.add(cheese);
        pizza.setIngredients(ingredients);

        return pizza;
    }

    protected Pizza createPizzaWithTwoBaseSauce(){
        Pizza pizza = new Pizza();
        pizza.setName("twosaucepizza");
        pizza.setSize(PizzaSize.NORMAL);
        pizza.setPrice(20.5f);
        pizza.setCustom(false);

        Set<Ingredient> ingredients = new HashSet<Ingredient>();
        Ingredient tomatoSauce = new Ingredient();
        tomatoSauce.setName("tomato sauce");
        tomatoSauce.setPrice(2.1f);
        tomatoSauce.setType(IngredientType.PIZZA_BASESAUCE);

        Ingredient sourCream = new Ingredient();
        sourCream.setName("sour cream");
        sourCream.setPrice(1.1f);
        sourCream.setType(IngredientType.PIZZA_BASESAUCE);

        Ingredient cheese = new Ingredient();
        cheese.setName("mozarella");
        cheese.setPrice(3.3f);
        cheese.setType(IngredientType.PIZZA_TOPPING);

        ingredients.add(tomatoSauce);
        ingredients.add(sourCream);
        ingredients.add(cheese);
        pizza.setIngredients(ingredients);

        return pizza;
    }
}
