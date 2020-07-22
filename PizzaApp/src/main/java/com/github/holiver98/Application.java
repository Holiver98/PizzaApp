package com.github.holiver98;

import com.github.holiver98.config.ApplicationConfiguration;
import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.IngredientType;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.PizzaSize;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.github.holiver98.service.IPizzaService;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);

        testingPizzaService(applicationContext);
    }

    private static void printBeanDefinitions(ApplicationContext applicationContext){
        for (String name : applicationContext.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }

    private static void testingPizzaService(ApplicationContext context){
        System.out.println("************");
        System.out.println("TESTING: PizzaService");
        IPizzaService pizzaService = context.getBean(IPizzaService.class);

        Pizza pizza = new Pizza();
        pizza.setCustom(false);
        pizza.setSize(PizzaSize.NORMAL);
        pizza.setName("Pepperoni pizzaroni");
        Set<Ingredient> ingredients = createTestSetOfIngredients();
        pizza.setIngredients(ingredients);
        pizza.setPrice(pizzaService.calculatePrice(pizza));

        pizzaService.savePizza(pizza);

        System.out.println();
        System.out.println("Pizzas retrieved: " + pizzaService.getPizzas());
        System.out.println("************");
    }

    private static Set<Ingredient> createTestSetOfIngredients(){
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

        return ingredients;
    }
}
