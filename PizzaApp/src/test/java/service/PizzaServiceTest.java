package service;

import dao.IIngredientDao;
import dao.IPizzaDao;
import model.Pizza;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PizzaServiceTest extends PizzaServiceTestBase{
    private IIngredientDao ingredientDao;
    private IPizzaDao pizzaDao;

    private PizzaService pizzaService;

    @BeforeEach
    void init(){
        ingredientDao = Mockito.mock(IIngredientDao.class);
        pizzaDao = Mockito.mock(IPizzaDao.class);
        pizzaService = new PizzaService(pizzaDao, ingredientDao);
    }

    @Test
    void calculatePrice_Given_3_Ingredients_With_Valid_Prices(){
        //Arrange
        Pizza pizza = createPizzaWith3IngredientsGivenThePrices(8.2f, 2.0f, 3.6f);

        //Act
        float price = pizzaService.calculatePrice(pizza);

        //Assert
        assertThat(price).isEqualTo(13.8f);
    }

    @Test
    void calculatePrice_Given_3_Ingredients_Some_Have_Invalid_Price(){
        //Arrange
        Pizza pizza = createPizzaWith3IngredientsGivenThePrices(-2.1f, -3.3f, 8.4f);

        //Act
        float price = pizzaService.calculatePrice(pizza);

        //Assert
        assertThat(price).isEqualTo(-1f);
    }

    @Test
    void calculatePrice_Given_3_Ingredients_Some_Have_Invalid_Price_2(){
        //Arrange
        Pizza pizza = createPizzaWith3IngredientsGivenThePrices(-10.1f, -3.3f, 8.4f);

        //Act
        float price = pizzaService.calculatePrice(pizza);

        //Assert
        assertThat(price).isEqualTo(-1f);
    }

    @Test
    void calculatePrice_Passing_Null_Argument(){
        //Arrange

        //Act
        float price = pizzaService.calculatePrice(null);

        //Assert
        assertThat(price).isEqualTo(-1f);
    }

    @Test
    void calculatePrice_Passing_Pizza_With_No_Ingredients(){
        //Arrange
        Pizza pizza = new Pizza();

        //Act
        float price = pizzaService.calculatePrice(pizza);

        //Assert
        assertThat(price).isEqualTo(-1f);
    }

}
