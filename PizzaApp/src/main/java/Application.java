import config.ApplicationConfiguration;
import dao.IPizzaDao;
import model.Ingredient;
import model.IngredientType;
import model.Pizza;
import model.PizzaSize;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.IPizzaService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

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
        System.out.println(pizzaService.getPizzas());
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
