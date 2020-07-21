package service;

import dao.IIngredientDao;
import dao.IPizzaDao;
import dao.IRatingDao;
import model.Pizza;
import model.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PizzaServiceTest extends PizzaServiceTestBase{
    private IIngredientDao ingredientDao;
    private IPizzaDao pizzaDao;
    private IRatingDao ratingDao;

    private PizzaService pizzaService;

    @BeforeEach
    void init(){
        ingredientDao = Mockito.mock(IIngredientDao.class);
        pizzaDao = Mockito.mock(IPizzaDao.class);
        ratingDao = Mockito.mock(IRatingDao.class);
        pizzaService = new PizzaService(pizzaDao, ingredientDao, ratingDao);
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

    @Test
    void recalculateRatingAverage_Should_Return_Correct_Value_And_Update_Pizza(){
        //Arrange
        long pizzaId = 1;
        Pizza pizza = new Pizza();
        pizza.setId(pizzaId);
        Mockito.when(pizzaDao.getPizzaById(pizzaId)).thenReturn(pizza);

        List<Rating> ratingsInDatabase = new ArrayList<Rating>();
        Rating ratingOfBob = new Rating();
        ratingOfBob.setPizzaId(pizzaId);
        ratingOfBob.setRating(3);
        ratingOfBob.setUserEmailAddress("bob123@dsa.hu");

        Rating ratingOfAlex = new Rating();
        ratingOfAlex.setPizzaId(pizzaId);
        ratingOfAlex.setRating(4);
        ratingOfAlex.setUserEmailAddress("alex55@dsa.hu");

        Rating ratingOfPete = new Rating();
        ratingOfPete.setPizzaId(pizzaId);
        ratingOfPete.setRating(1);
        ratingOfPete.setUserEmailAddress("pete88@dsa.hu");

        ratingsInDatabase.add(ratingOfBob);
        ratingsInDatabase.add(ratingOfAlex);
        ratingsInDatabase.add(ratingOfPete);

        Mockito.when(ratingDao.getRatingsOfPizza(pizzaId)).thenReturn(ratingsInDatabase);

        //Act
        float newRatingAverage = pizzaService.recalculateRatingAverage(pizzaId);

        //Assert
        float expectedValue = 2.66666667f;
        assertThat(newRatingAverage).isEqualTo(expectedValue);
        assertThat(pizza.getRatingAverage()).isEqualTo(expectedValue);
    }

    @Test
    void recalculateRatingAverage_Should_Return_Correct_Value(){
        //Arrange
        long pizzaId = 1;
        Pizza pizza = new Pizza();
        pizza.setId(pizzaId);
        Mockito.when(pizzaDao.getPizzaById(pizzaId)).thenReturn(pizza);

        List<Rating> ratingsInDatabase = new ArrayList<Rating>();
        Rating ratingOfBob = new Rating();
        ratingOfBob.setPizzaId(pizzaId);
        ratingOfBob.setRating(3);
        ratingOfBob.setUserEmailAddress("bob123@dsa.hu");

        Rating ratingOfAlex = new Rating();
        ratingOfAlex.setPizzaId(pizzaId);
        ratingOfAlex.setRating(4);
        ratingOfAlex.setUserEmailAddress("alex55@dsa.hu");

        Rating ratingOfPete = new Rating();
        ratingOfPete.setPizzaId(pizzaId);
        ratingOfPete.setRating(2);
        ratingOfPete.setUserEmailAddress("pete88@dsa.hu");

        ratingsInDatabase.add(ratingOfBob);
        ratingsInDatabase.add(ratingOfAlex);
        ratingsInDatabase.add(ratingOfPete);

        Mockito.when(ratingDao.getRatingsOfPizza(pizzaId)).thenReturn(ratingsInDatabase);

        //Act
        float newRatingAverage = pizzaService.recalculateRatingAverage(pizzaId);

        //Assert
        float expectedValue = 3f;
        assertThat(newRatingAverage).isEqualTo(expectedValue);
    }

    @Test
    void recalculateRatingAverage_Should_Return_Correct_Value_2(){
        //Arrange
        long pizzaId = 1;
        Pizza pizza = new Pizza();
        pizza.setId(pizzaId);
        Mockito.when(pizzaDao.getPizzaById(pizzaId)).thenReturn(pizza);

        List<Rating> ratingsInDatabase = new ArrayList<Rating>();
        Rating ratingOfBob = new Rating();
        ratingOfBob.setPizzaId(pizzaId);
        ratingOfBob.setRating(5);
        ratingOfBob.setUserEmailAddress("bob123@dsa.hu");

        Rating ratingOfAlex = new Rating();
        ratingOfAlex.setPizzaId(pizzaId);
        ratingOfAlex.setRating(1);
        ratingOfAlex.setUserEmailAddress("alex55@dsa.hu");

        Rating ratingOfPete = new Rating();
        ratingOfPete.setPizzaId(pizzaId);
        ratingOfPete.setRating(1);
        ratingOfPete.setUserEmailAddress("pete88@dsa.hu");

        ratingsInDatabase.add(ratingOfBob);
        ratingsInDatabase.add(ratingOfAlex);
        ratingsInDatabase.add(ratingOfPete);

        Mockito.when(ratingDao.getRatingsOfPizza(pizzaId)).thenReturn(ratingsInDatabase);

        //Act
        float newRatingAverage = pizzaService.recalculateRatingAverage(pizzaId);

        //Assert
        float expectedValue = 2.33333333f;
        assertThat(newRatingAverage).isEqualTo(expectedValue);
    }

    @Test
    void recalculateRatingAverage_No_Rating_For_Pizza_Should_Return_0_And_Update_Pizza(){
        //Arrange
        long pizzaId = 1;
        Pizza pizza = new Pizza();
        pizza.setId(pizzaId);
        Mockito.lenient().when(pizzaDao.getPizzaById(pizzaId)).thenReturn(pizza);

        List<Rating> ratingsInDatabase = new ArrayList<Rating>();
        Mockito.when(ratingDao.getRatingsOfPizza(pizzaId)).thenReturn(ratingsInDatabase);

        //Act
        float newRatingAverage = pizzaService.recalculateRatingAverage(pizzaId);

        //Assert
        assertThat(newRatingAverage).isEqualTo(0f);
        assertThat(pizza.getRatingAverage()).isEqualTo(0f);
    }

    @Test
    void recalculateRatingAverage_Pizza_Not_In_Database_Should_Return_Minus_1(){
        //Arrange
        long pizzaId = 43;
        Mockito.lenient().when(pizzaDao.getPizzaById(pizzaId)).thenReturn(null);

        List<Rating> ratingsInDatabase = new ArrayList<Rating>();
        Mockito.lenient().when(ratingDao.getRatingsOfPizza(pizzaId)).thenReturn(ratingsInDatabase);

        //Act
        float result = pizzaService.recalculateRatingAverage(pizzaId);

        //Assert
        assertThat(result).isEqualTo(-1f);
    }
}
