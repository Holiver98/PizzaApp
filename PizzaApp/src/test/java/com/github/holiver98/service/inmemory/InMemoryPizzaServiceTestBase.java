package com.github.holiver98.service.inmemory;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.IngredientType;
import com.github.holiver98.model.Pizza;

import java.util.HashSet;
import java.util.Set;

public class InMemoryPizzaServiceTestBase {
    protected Pizza createPizzaWith3IngredientsGivenThePrices(float ingredientPrice1,
                                                              float ingredientPrice2,
                                                              float ingredientPrice3){
        Pizza pizza = new Pizza();
        pizza.setId(1L);
        Set<Ingredient> ingredients = new HashSet<Ingredient>();
        Ingredient tomatoSauce = new Ingredient();
        tomatoSauce.setName("tomato sauce");
        tomatoSauce.setPrice(ingredientPrice1);
        tomatoSauce.setType(IngredientType.PIZZA_BASESAUCE);

        Ingredient cheese = new Ingredient();
        cheese.setName("mozarella");
        cheese.setPrice(ingredientPrice2);
        cheese.setType(IngredientType.PIZZA_TOPPING);

        Ingredient ham = new Ingredient();
        ham.setName("ham");
        ham.setPrice(ingredientPrice3);
        ham.setType(IngredientType.PIZZA_TOPPING);

        ingredients.add(tomatoSauce);
        ingredients.add(cheese);
        ingredients.add(ham);
        pizza.setIngredients(ingredients);

        return pizza;
    }
}
