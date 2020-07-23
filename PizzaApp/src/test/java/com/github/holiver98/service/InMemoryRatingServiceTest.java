package com.github.holiver98.service;

import com.github.holiver98.dao.IInMemoryRatingDao;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.PizzaSize;
import com.github.holiver98.model.Rating;
import com.github.holiver98.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InMemoryRatingServiceTest {

    private IPizzaService pizzaService;
    private IUserService userService;
    private IInMemoryRatingDao ratingDao;

    private InMemoryRatingService ratingService;

    @BeforeEach
    void init(){
        pizzaService = Mockito.mock(IPizzaService.class);
        userService = Mockito.mock(IUserService.class);
        ratingDao = Mockito.mock(IInMemoryRatingDao.class);
        ratingService = new InMemoryRatingService(ratingDao, userService, pizzaService);
    }

    @Test
    void ratePizza_Pizza_Already_Rated_By_This_User_Should_Not_Rate_Pizza(){
        //Arrange
        final int rating = 3;
        final long pizzaId = 1;

        Pizza pizza = new Pizza();
        pizza.setId(pizzaId);
        pizza.setName("Pepperoni Pizza");
        pizza.setSize(PizzaSize.NORMAL);

        User loggedInUser = new User();
        loggedInUser.setUsername("Simon");
        loggedInUser.setEmailAddress("dsadsa@dsdas.hu");
        loggedInUser.setPassword("123123dsa");

        Rating ratingOnPizzaByLoggedInUser = new Rating();
        ratingOnPizzaByLoggedInUser.setUserEmailAddress(loggedInUser.getEmailAddress());
        ratingOnPizzaByLoggedInUser.setRating(rating);
        ratingOnPizzaByLoggedInUser.setPizzaId(pizza.getId());

        Mockito.when(pizzaService.getPizzaById(pizzaId)).thenReturn(pizza);
        Mockito.when(userService.getLoggedInUser()).thenReturn(loggedInUser);
        Mockito.when(ratingDao.getRatingOfUserForPizza(loggedInUser.getEmailAddress(), pizzaId)).thenReturn(ratingOnPizzaByLoggedInUser);

        //Act
        ratingService.ratePizza(pizzaId, rating);

        //Assert
        Mockito.verify(ratingDao, Mockito.times(0)).saveRating(Mockito.any());
        Mockito.verify(pizzaService, Mockito.times(0)).recalculateRatingAverage(Mockito.anyLong());
    }

    @Test
    void ratePizza_Happy_Path_Should_Rate_Pizza(){
        //Arrange
        final int rating = 3;
        final long pizzaId = 1;

        Pizza pizza = new Pizza();
        pizza.setId(pizzaId);
        pizza.setName("Pepperoni Pizza");
        pizza.setSize(PizzaSize.NORMAL);

        User loggedInUser = new User();
        loggedInUser.setUsername("Simon");
        loggedInUser.setEmailAddress("dsadsa@dsdas.hu");
        loggedInUser.setPassword("123123dsa");

        Mockito.when(pizzaService.getPizzaById(pizzaId)).thenReturn(pizza);
        Mockito.when(userService.getLoggedInUser()).thenReturn(loggedInUser);
        Mockito.when(ratingDao.getRatingOfUserForPizza(loggedInUser.getEmailAddress(), pizzaId)).thenReturn(null);

        //Act
        ratingService.ratePizza(pizzaId, rating);

        //Assert
        Rating ratingTheUserShouldGive = new Rating();
        ratingTheUserShouldGive.setPizzaId(pizza.getId());
        ratingTheUserShouldGive.setRating(rating);
        ratingTheUserShouldGive.setUserEmailAddress(loggedInUser.getEmailAddress());
        Mockito.verify(ratingDao, Mockito.times(1)).saveRating(ratingTheUserShouldGive);
        Mockito.verify(pizzaService, Mockito.times(1)).recalculateRatingAverage(pizzaId);
    }

    @Test
    void ratePizza_User_Isnt_Logged_In_Should_Not_Rate_Pizza(){
        //Arrange
        final int rating = 3;
        final long pizzaId = 1;

        Pizza pizza = new Pizza();
        pizza.setId(pizzaId);
        pizza.setName("Pepperoni Pizza");
        pizza.setSize(PizzaSize.NORMAL);

        User loggedInUser = new User();
        loggedInUser.setUsername("Simon");
        loggedInUser.setEmailAddress("dsadsa@dsdas.hu");
        loggedInUser.setPassword("123123dsa");

        Rating ratingOnPizzaByLoggedInUser = new Rating();
        ratingOnPizzaByLoggedInUser.setUserEmailAddress(loggedInUser.getEmailAddress());
        ratingOnPizzaByLoggedInUser.setRating(rating);
        ratingOnPizzaByLoggedInUser.setPizzaId(pizza.getId());

        Mockito.lenient().when(pizzaService.getPizzaById(pizzaId)).thenReturn(pizza);
        Mockito.when(userService.getLoggedInUser()).thenReturn(null);
        Mockito.lenient().when(ratingDao.getRatingOfUserForPizza(loggedInUser.getEmailAddress(), pizzaId)).thenReturn(ratingOnPizzaByLoggedInUser);

        //Act
        ratingService.ratePizza(pizzaId, rating);

        //Assert
        Mockito.verify(ratingDao, Mockito.times(0)).saveRating(Mockito.any());
        Mockito.verify(pizzaService, Mockito.times(0)).recalculateRatingAverage(Mockito.anyLong());
    }

    @Test
    void ratePizza_Pizza_Doesnt_Exist_Should_Not_Rate_Pizza(){
        //Arrange
        final int rating = 3;
        final long pizzaId = 1;

        Pizza pizza = new Pizza();
        pizza.setId(pizzaId);
        pizza.setName("Pepperoni Pizza");
        pizza.setSize(PizzaSize.NORMAL);

        User loggedInUser = new User();
        loggedInUser.setUsername("Simon");
        loggedInUser.setEmailAddress("dsadsa@dsdas.hu");
        loggedInUser.setPassword("123123dsa");

        Rating ratingOnPizzaByLoggedInUser = new Rating();
        ratingOnPizzaByLoggedInUser.setUserEmailAddress(loggedInUser.getEmailAddress());
        ratingOnPizzaByLoggedInUser.setRating(rating);
        ratingOnPizzaByLoggedInUser.setPizzaId(pizza.getId());

        Mockito.when(pizzaService.getPizzaById(pizzaId)).thenReturn(null);
        Mockito.when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        //Act
        ratingService.ratePizza(pizzaId, rating);

        //Assert
        Mockito.verify(ratingDao, Mockito.times(0)).saveRating(Mockito.any());
        Mockito.verify(pizzaService, Mockito.times(0)).recalculateRatingAverage(Mockito.anyLong());
    }

    @Test
    void ratePizza_Rating_Lower_Than_Required_Should_Not_Rate_Pizza(){
        //Arrange
        final int rating = 0;
        final long pizzaId = 1;

        Pizza pizza = new Pizza();
        pizza.setId(pizzaId);
        pizza.setName("Pepperoni Pizza");
        pizza.setSize(PizzaSize.NORMAL);

        User loggedInUser = new User();
        loggedInUser.setUsername("Simon");
        loggedInUser.setEmailAddress("dsadsa@dsdas.hu");
        loggedInUser.setPassword("123123dsa");

        Mockito.lenient().when(pizzaService.getPizzaById(pizzaId)).thenReturn(pizza);
        Mockito.lenient().when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        //Act
        ratingService.ratePizza(pizzaId, rating);

        //Assert
        Mockito.verify(ratingDao, Mockito.times(0)).saveRating(Mockito.any());
        Mockito.verify(pizzaService, Mockito.times(0)).recalculateRatingAverage(Mockito.anyLong());
    }

    @Test
    void ratePizza_Rating_Lower_Than_Required_Should_Not_Rate_Pizza_2(){
        //Arrange
        final int rating = -14;
        final long pizzaId = 1;

        Pizza pizza = new Pizza();
        pizza.setId(pizzaId);
        pizza.setName("Pepperoni Pizza");
        pizza.setSize(PizzaSize.NORMAL);

        User loggedInUser = new User();
        loggedInUser.setUsername("Simon");
        loggedInUser.setEmailAddress("dsadsa@dsdas.hu");
        loggedInUser.setPassword("123123dsa");

        Mockito.lenient().when(pizzaService.getPizzaById(pizzaId)).thenReturn(pizza);
        Mockito.lenient().when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        //Act
        ratingService.ratePizza(pizzaId, rating);

        //Assert
        Mockito.verify(ratingDao, Mockito.times(0)).saveRating(Mockito.any());
        Mockito.verify(pizzaService, Mockito.times(0)).recalculateRatingAverage(Mockito.anyLong());
    }

    @Test
    void ratePizza_Rating_Higher_Than_Required_Should_Not_Rate_Pizza(){
        //Arrange
        final int rating = 6;
        final long pizzaId = 1;

        Pizza pizza = new Pizza();
        pizza.setId(pizzaId);
        pizza.setName("Pepperoni Pizza");
        pizza.setSize(PizzaSize.NORMAL);

        User loggedInUser = new User();
        loggedInUser.setUsername("Simon");
        loggedInUser.setEmailAddress("dsadsa@dsdas.hu");
        loggedInUser.setPassword("123123dsa");

        Mockito.lenient().when(pizzaService.getPizzaById(pizzaId)).thenReturn(pizza);
        Mockito.lenient().when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        //Act
        ratingService.ratePizza(pizzaId, rating);

        //Assert
        Mockito.verify(ratingDao, Mockito.times(0)).saveRating(Mockito.any());
        Mockito.verify(pizzaService, Mockito.times(0)).recalculateRatingAverage(Mockito.anyLong());
    }

    @Test
    void ratePizza_Rating_Higher_Than_Required_Should_Not_Rate_Pizza_2(){
        //Arrange
        final int rating = 13;
        final long pizzaId = 1;

        Pizza pizza = new Pizza();
        pizza.setId(pizzaId);
        pizza.setName("Pepperoni Pizza");
        pizza.setSize(PizzaSize.NORMAL);

        User loggedInUser = new User();
        loggedInUser.setUsername("Simon");
        loggedInUser.setEmailAddress("dsadsa@dsdas.hu");
        loggedInUser.setPassword("123123dsa");

        Mockito.lenient().when(pizzaService.getPizzaById(pizzaId)).thenReturn(pizza);
        Mockito.lenient().when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        //Act
        ratingService.ratePizza(pizzaId, rating);

        //Assert
        Mockito.verify(ratingDao, Mockito.times(0)).saveRating(Mockito.any());
        Mockito.verify(pizzaService, Mockito.times(0)).recalculateRatingAverage(Mockito.anyLong());
    }

    @Test
    void ratePizza_Rating_Valid_Lower_Limit_Should_Rate_Pizza(){
        //Arrange
        final int rating = 1;
        final long pizzaId = 1;

        Pizza pizza = new Pizza();
        pizza.setId(pizzaId);
        pizza.setName("Pepperoni Pizza");
        pizza.setSize(PizzaSize.NORMAL);

        User loggedInUser = new User();
        loggedInUser.setUsername("Simon");
        loggedInUser.setEmailAddress("dsadsa@dsdas.hu");
        loggedInUser.setPassword("123123dsa");

        Mockito.lenient().when(pizzaService.getPizzaById(pizzaId)).thenReturn(pizza);
        Mockito.lenient().when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        //Act
        ratingService.ratePizza(pizzaId, rating);

        //Assert
        Mockito.verify(ratingDao, Mockito.times(1)).saveRating(Mockito.any());
        Mockito.verify(pizzaService, Mockito.times(1)).recalculateRatingAverage(pizzaId);
    }

    @Test
    void ratePizza_Rating_Valid_Upper_Limit_Should_Rate_Pizza(){
        //Arrange
        final int rating = 5;
        final long pizzaId = 1;

        Pizza pizza = new Pizza();
        pizza.setId(pizzaId);
        pizza.setName("Pepperoni Pizza");
        pizza.setSize(PizzaSize.NORMAL);

        User loggedInUser = new User();
        loggedInUser.setUsername("Simon");
        loggedInUser.setEmailAddress("dsadsa@dsdas.hu");
        loggedInUser.setPassword("123123dsa");

        Mockito.lenient().when(pizzaService.getPizzaById(pizzaId)).thenReturn(pizza);
        Mockito.lenient().when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        //Act
        ratingService.ratePizza(pizzaId, rating);

        //Assert
        Mockito.verify(ratingDao, Mockito.times(1)).saveRating(Mockito.any());
        Mockito.verify(pizzaService, Mockito.times(1)).recalculateRatingAverage(pizzaId);
    }
}
