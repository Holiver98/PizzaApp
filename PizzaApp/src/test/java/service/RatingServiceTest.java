package service;

import dao.IRatingDao;
import model.Pizza;
import model.PizzaSize;
import model.Rating;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    private IPizzaService pizzaService;
    private IUserService userService;
    private IRatingDao ratingDao;

    private RatingService ratingService;

    @BeforeEach
    void init(){
        pizzaService = Mockito.mock(IPizzaService.class);
        userService = Mockito.mock(IUserService.class);
        ratingDao = Mockito.mock(IRatingDao.class);
        ratingService = new RatingService(ratingDao, userService, pizzaService);
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
        Mockito.lenient().when(ratingDao.getRatingOfUserForPizza(loggedInUser.getEmailAddress(), pizzaId)).thenReturn(ratingOnPizzaByLoggedInUser);

        //Act
        ratingService.ratePizza(pizzaId, rating);

        //Assert
        Mockito.verify(ratingDao, Mockito.times(0)).saveRating(Mockito.any());
        Mockito.verify(pizzaService, Mockito.times(0)).recalculateRatingAverage(Mockito.anyLong());
    }
}
