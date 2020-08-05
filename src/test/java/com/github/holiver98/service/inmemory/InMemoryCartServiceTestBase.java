package com.github.holiver98.service.inmemory;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.IngredientType;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.PizzaSize;

import java.util.HashSet;
import java.util.Set;

//TODO: +1
public class InMemoryCartServiceTestBase {
    protected Pizza createValidPizza(String name, Long id){
        Pizza pizza = new Pizza();
        pizza.setName(name);
        pizza.setSize(PizzaSize.NORMAL);
        pizza.setPrice(20.5f);
        pizza.setCustom(false);
        pizza.setId(id);

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
