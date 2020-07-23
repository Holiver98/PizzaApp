package com.github.holiver98.dal.jpa;

import com.github.holiver98.dal.jpa.IIngredientRepository;
import com.github.holiver98.dal.jpa.IPizzaRepository;
import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.IngredientType;
import com.github.holiver98.model.Pizza;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

@DataJpaTest
public class PizzaRepositoryTest {

    @Autowired
    IPizzaRepository repo;
    @Autowired
    IIngredientRepository inRepo;

    @Test
    void test1(){
        Pizza pizza = new Pizza();
        pizza.setId(12);
        Set<Ingredient> ingredients = createTestSetOfIngredients();
        pizza.setIngredients(ingredients);

        Pizza savedP = repo.save(pizza);
        System.out.println(savedP);
    }

    private Set<Ingredient> createTestSetOfIngredients(){
        Set<Ingredient> ingredients = new HashSet<Ingredient>();

        Ingredient onion = new Ingredient();
        onion.setType(IngredientType.PIZZA_TOPPING);
        onion.setPrice(3.14f);
        onion.setName("Onion");

        Ingredient tomato = new Ingredient();
        tomato.setType(IngredientType.PIZZA_BASESAUCE);
        tomato.setPrice(2.1f);
        tomato.setName("Tomato");

        Ingredient cheese = new Ingredient();
        cheese.setType(IngredientType.PIZZA_TOPPING);
        cheese.setPrice(4.5f);
        cheese.setName("Cheese");

        ingredients.add(onion);
        ingredients.add(tomato);
        ingredients.add(cheese);

        inRepo.save(onion);
        inRepo.save(tomato);
        inRepo.save(cheese);

        return ingredients;
    }
}
