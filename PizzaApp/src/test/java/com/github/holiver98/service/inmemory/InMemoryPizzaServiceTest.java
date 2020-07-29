package com.github.holiver98.service.inmemory;

import com.github.holiver98.dal.inmemory.IInMemoryIngredientDao;
import com.github.holiver98.dal.inmemory.IInMemoryPizzaDao;
import com.github.holiver98.dal.inmemory.IInMemoryRatingDao;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.Rating;
import com.github.holiver98.service.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class InMemoryPizzaServiceTest extends InMemoryPizzaServiceTestBase {
    private IInMemoryIngredientDao ingredientDao;
    private IInMemoryPizzaDao pizzaDao;
    private IInMemoryRatingDao ratingDao;

    private InMemoryPizzaService pizzaService;

    @BeforeEach
    void init(){
        ingredientDao = Mockito.mock(IInMemoryIngredientDao.class);
        pizzaDao = Mockito.mock(IInMemoryPizzaDao.class);
        ratingDao = Mockito.mock(IInMemoryRatingDao.class);
        pizzaService = new InMemoryPizzaService(pizzaDao, ingredientDao, ratingDao);
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
    void calculatePrice_Given_3_Ingredients_Some_Have_Invalid_Price_Should_Throw_Exception(){
        //Arrange
        Pizza pizza = createPizzaWith3IngredientsGivenThePrices(-2.1f, -3.3f, 8.4f);

        //Act
        //Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> pizzaService.calculatePrice(pizza));
    }

    @Test
    void calculatePrice_Given_3_Ingredients_Some_Have_Invalid_Price_Should_Throw_Exception_2(){
        //Arrange
        Pizza pizza = createPizzaWith3IngredientsGivenThePrices(-10.1f, -3.3f, 8.4f);

        //Act
        //Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> pizzaService.calculatePrice(pizza));
    }

    @Test
    void calculatePrice_Passing_Null_Argument_Should_Throw_Exception(){
        Assertions.assertThrows(NullPointerException.class,
                () -> pizzaService.calculatePrice(null));
    }

    @Test
    void calculatePrice_Passing_Pizza_With_No_Ingredients_Should_Throw_Exception(){
        //Arrange
        Pizza pizza = new Pizza();

        //Act
        //Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> pizzaService.calculatePrice(pizza));
    }

    @Test
    void recalculateRatingAverage_Should_Return_Correct_Value_And_Update_Pizza() throws NotFoundException {
        //Arrange
        long pizzaId = 1;
        Pizza validPizza = createPizzaWith3IngredientsGivenThePrices(3.1f, 2.2f, 3.2f);
        validPizza.setId(pizzaId);
        Mockito.when(pizzaDao.getPizzaById(pizzaId)).thenReturn(validPizza);

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
        assertThat(validPizza.getRatingAverage()).isEqualTo(expectedValue);
    }

    @Test
    void recalculateRatingAverage_Should_Return_Correct_Value() throws NotFoundException {
        //Arrange
        long pizzaId = 1;
        Pizza validPizza = createPizzaWith3IngredientsGivenThePrices(3.1f, 2.2f, 3.2f);
        validPizza.setId(pizzaId);
        Mockito.when(pizzaDao.getPizzaById(pizzaId)).thenReturn(validPizza);

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
    void recalculateRatingAverage_Should_Return_Correct_Value_2() throws NotFoundException {
        //Arrange
        long pizzaId = 1;
        Pizza validPizza = createPizzaWith3IngredientsGivenThePrices(3.1f, 2.2f, 3.2f);
        validPizza.setId(pizzaId);
        Mockito.when(pizzaDao.getPizzaById(pizzaId)).thenReturn(validPizza);

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
    void recalculateRatingAverage_No_Rating_For_Pizza_Should_Return_0_And_Update_Pizza() throws NotFoundException {
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
    void recalculateRatingAverage_Pizza_Not_In_Database_Should_Throw_Exception(){
        //Arrange
        long pizzaId = 43;
        Mockito.lenient().when(pizzaDao.getPizzaById(pizzaId)).thenReturn(null);

        List<Rating> ratingsInDatabase = new ArrayList<Rating>();
        Mockito.lenient().when(ratingDao.getRatingsOfPizza(pizzaId)).thenReturn(ratingsInDatabase);

        //Act
        //Assert
        Assertions.assertThrows(NotFoundException.class,
                () -> pizzaService.recalculateRatingAverage(pizzaId));
    }
}
