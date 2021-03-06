package com.github.holiver98.service.inmemory;

import com.github.holiver98.dal.inmemory.IInMemoryRatingDao;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.PizzaSize;
import com.github.holiver98.model.Rating;
import com.github.holiver98.model.User;
import com.github.holiver98.service.IPizzaService;
import com.github.holiver98.service.IUserService;
import com.github.holiver98.service.NoPermissionException;
import com.github.holiver98.service.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class InMemoryRatingServiceTest {

    private IPizzaService pizzaService;
    private IUserService userService;
    private IInMemoryRatingDao ratingDao;

    private InMemoryRatingService ratingService;

    @Captor
    ArgumentCaptor<Pizza> pizzaArgumentCaptor;

    @BeforeEach
    void init(){
        pizzaService = Mockito.mock(IPizzaService.class);
        userService = Mockito.mock(IUserService.class);
        ratingDao = Mockito.mock(IInMemoryRatingDao.class);
        ratingService = new InMemoryRatingService(ratingDao, userService, pizzaService);
    }

    @Test
    void ratePizza_Pizza_Already_Rated_By_This_User_Should_Not_Rate_Pizza() throws NotFoundException {
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

        Mockito.when(pizzaService.getPizzaById(pizzaId)).thenReturn(Optional.of(pizza));
        Mockito.lenient().when(userService.getUser(loggedInUser.getEmailAddress())).thenReturn(Optional.of(loggedInUser));
        Mockito.when(ratingDao.getRatingOfUserForPizza(loggedInUser.getEmailAddress(), pizzaId)).thenReturn(Optional.of(ratingOnPizzaByLoggedInUser));

        //Act
        //Assert
        Assertions.assertThrows(UnsupportedOperationException.class, () -> ratingService.ratePizza(pizzaId, rating, loggedInUser.getEmailAddress()));
    }

    @Test
    void ratePizza_Happy_Path_Should_Rate_Pizza() throws NotFoundException {
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

        Rating ratingTheUserShouldGive = new Rating();
        ratingTheUserShouldGive.setPizzaId(pizza.getId());
        ratingTheUserShouldGive.setRating(rating);
        ratingTheUserShouldGive.setUserEmailAddress(loggedInUser.getEmailAddress());

        Mockito.when(pizzaService.getPizzaById(pizzaId)).thenReturn(Optional.of(pizza));
        Mockito.lenient().when(userService.getUser(loggedInUser.getEmailAddress())).thenReturn(Optional.of(loggedInUser));

        List<Rating> listAfterTheRatingHasBeenSaved = new ArrayList<Rating>();
        listAfterTheRatingHasBeenSaved.add(ratingTheUserShouldGive);
        Mockito.when(ratingService.getRatingsOfPizza(pizza.getId()))
                .thenReturn(listAfterTheRatingHasBeenSaved);
        //Act
        ratingService.ratePizza(pizzaId, rating, loggedInUser.getEmailAddress());

        //Assert
        Mockito.verify(ratingDao, Mockito.times(1)).saveRating(ratingTheUserShouldGive);
        Mockito.verify(pizzaService, Mockito.times(1)).updatePizzaWithoutAuthentication(pizzaArgumentCaptor.capture());
        Pizza pizzaUpdateData = pizzaArgumentCaptor.getValue();
        assertThat(pizzaUpdateData.getId()).isEqualTo(pizza.getId());

        BigDecimal actualRatingAverage = pizzaUpdateData.getRatingAverage().stripTrailingZeros();
        BigDecimal expectedRatingAverage = BigDecimal.valueOf(3).stripTrailingZeros();
        assertThat(actualRatingAverage).isEqualTo(expectedRatingAverage);
    }

    @Test
    void ratePizza_Pizza_Doesnt_Exist_Should_Throw_Exception() throws NotFoundException {
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

        Mockito.when(pizzaService.getPizzaById(pizzaId)).thenReturn(Optional.empty());
        Mockito.lenient().when(userService.getUser(loggedInUser.getEmailAddress())).thenReturn(Optional.of(loggedInUser));

        //Act
        //Assert
        Assertions.assertThrows(NotFoundException.class, () ->  ratingService.ratePizza(pizzaId, rating, loggedInUser.getEmailAddress()));
    }

    @Test
    void ratePizza_Rating_Lower_Than_Required_Should_Throw_Exception() throws NotFoundException {
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

        Mockito.lenient().when(pizzaService.getPizzaById(pizzaId)).thenReturn(Optional.of(pizza));
        Mockito.lenient().when(userService.getUser(loggedInUser.getEmailAddress())).thenReturn(Optional.of(loggedInUser));

        //Act
        //Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> ratingService.ratePizza(pizzaId, rating, loggedInUser.getEmailAddress()));
    }

    @Test
    void ratePizza_Rating_Lower_Than_Required_Should_Throw_Exception_2() throws NotFoundException {
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

        Mockito.lenient().when(pizzaService.getPizzaById(pizzaId)).thenReturn(Optional.of(pizza));
        Mockito.lenient().when(userService.getUser(loggedInUser.getEmailAddress())).thenReturn(Optional.of(loggedInUser));

        //Act
        //Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> ratingService.ratePizza(pizzaId, rating, loggedInUser.getEmailAddress()));
    }

    @Test
    void ratePizza_Rating_Higher_Than_Required_Should_Throw_Exception() throws NotFoundException {
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

        Mockito.lenient().when(pizzaService.getPizzaById(pizzaId)).thenReturn(Optional.of(pizza));
        Mockito.lenient().when(userService.getUser(loggedInUser.getEmailAddress())).thenReturn(Optional.of(loggedInUser));

        //Act
        //Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> ratingService.ratePizza(pizzaId, rating, loggedInUser.getEmailAddress()));
    }

    @Test
    void ratePizza_Rating_Higher_Than_Required_Should_Throw_Exception_2() throws NotFoundException {
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

        Mockito.lenient().when(pizzaService.getPizzaById(pizzaId)).thenReturn(Optional.of(pizza));
        Mockito.lenient().when(userService.getUser(loggedInUser.getEmailAddress())).thenReturn(Optional.of(loggedInUser));

        //Act
        //Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> ratingService.ratePizza(pizzaId, rating, loggedInUser.getEmailAddress()));
    }

    @Test
    void ratePizza_Rating_Valid_Lower_Limit_Should_Rate_Pizza() throws NotFoundException {
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

        Rating ratingTheUserShouldGive = new Rating();
        ratingTheUserShouldGive.setPizzaId(pizza.getId());
        ratingTheUserShouldGive.setRating(rating);
        ratingTheUserShouldGive.setUserEmailAddress(loggedInUser.getEmailAddress());

        Mockito.lenient().when(pizzaService.getPizzaById(pizzaId)).thenReturn(Optional.of(pizza));
        Mockito.lenient().when(userService.getUser(loggedInUser.getEmailAddress())).thenReturn(Optional.of(loggedInUser));

        List<Rating> listAfterTheRatingHasBeenSaved = new ArrayList<Rating>();
        listAfterTheRatingHasBeenSaved.add(ratingTheUserShouldGive);
        Mockito.when(ratingService.getRatingsOfPizza(pizza.getId()))
                .thenReturn(listAfterTheRatingHasBeenSaved);

        //Act
        ratingService.ratePizza(pizzaId, rating, loggedInUser.getEmailAddress());

        //Assert
        Mockito.verify(ratingDao, Mockito.times(1)).saveRating(ratingTheUserShouldGive);
        Mockito.verify(pizzaService, Mockito.times(1)).updatePizzaWithoutAuthentication(pizzaArgumentCaptor.capture());
        Pizza pizzaUpdateData = pizzaArgumentCaptor.getValue();
        assertThat(pizzaUpdateData.getId()).isEqualTo(pizza.getId());


        BigDecimal actualRatingAverage = pizzaUpdateData.getRatingAverage().stripTrailingZeros();
        BigDecimal expectedRatingAverage = BigDecimal.valueOf(1).stripTrailingZeros();
        assertThat(actualRatingAverage).isEqualTo(expectedRatingAverage);
    }

    @Test
    void ratePizza_Rating_Valid_Upper_Limit_Should_Rate_Pizza() throws NotFoundException {
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

        Rating ratingTheUserShouldGive = new Rating();
        ratingTheUserShouldGive.setPizzaId(pizza.getId());
        ratingTheUserShouldGive.setRating(rating);
        ratingTheUserShouldGive.setUserEmailAddress(loggedInUser.getEmailAddress());

        Mockito.lenient().when(pizzaService.getPizzaById(pizzaId)).thenReturn(Optional.of(pizza));
        Mockito.lenient().when(userService.getUser(loggedInUser.getEmailAddress())).thenReturn(Optional.of(loggedInUser));

        List<Rating> listAfterTheRatingHasBeenSaved = new ArrayList<Rating>();
        listAfterTheRatingHasBeenSaved.add(ratingTheUserShouldGive);
        Mockito.when(ratingService.getRatingsOfPizza(pizza.getId()))
                .thenReturn(listAfterTheRatingHasBeenSaved);

        //Act
        ratingService.ratePizza(pizzaId, rating, loggedInUser.getEmailAddress());

        //Assert
        Mockito.verify(ratingDao, Mockito.times(1)).saveRating(Mockito.any());
        Mockito.verify(pizzaService, Mockito.times(1)).updatePizzaWithoutAuthentication(pizzaArgumentCaptor.capture());
        Pizza pizzaUpdateData = pizzaArgumentCaptor.getValue();
        assertThat(pizzaUpdateData.getId()).isEqualTo(pizza.getId());

        BigDecimal actualRatingAverage = pizzaUpdateData.getRatingAverage().stripTrailingZeros();
        BigDecimal expectedRatingAverage = BigDecimal.valueOf(5).stripTrailingZeros();
        assertThat(actualRatingAverage).isEqualTo(expectedRatingAverage);
    }

    @Test
    void ratePizza_recalculateRatingAverage_Should_Return_Correct_Value_And_Update_Pizza() throws NotFoundException {
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

        Rating ratingTheUserShouldGive = new Rating();
        ratingTheUserShouldGive.setPizzaId(pizza.getId());
        ratingTheUserShouldGive.setRating(rating);
        ratingTheUserShouldGive.setUserEmailAddress(loggedInUser.getEmailAddress());

        Mockito.lenient().when(pizzaService.getPizzaById(pizzaId)).thenReturn(Optional.of(pizza));
        Mockito.lenient().when(userService.getUser(loggedInUser.getEmailAddress())).thenReturn(Optional.of(loggedInUser));

        List<Rating> savedRatings = new ArrayList<Rating>();
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

        savedRatings.add(ratingOfBob);
        savedRatings.add(ratingOfAlex);
        savedRatings.add(ratingOfPete);

        List<Rating> listAfterTheRatingHasBeenSaved = new ArrayList<Rating>(savedRatings);
        listAfterTheRatingHasBeenSaved.add(ratingTheUserShouldGive);
        Mockito.when(ratingService.getRatingsOfPizza(pizza.getId()))
                .thenReturn(listAfterTheRatingHasBeenSaved);

        //Act
        ratingService.ratePizza(pizzaId, rating, loggedInUser.getEmailAddress());

        //Assert
        Mockito.verify(ratingDao, Mockito.times(1)).saveRating(ratingTheUserShouldGive);
        Mockito.verify(pizzaService, Mockito.times(1)).updatePizzaWithoutAuthentication(pizzaArgumentCaptor.capture());
        Pizza pizzaUpdateData = pizzaArgumentCaptor.getValue();
        assertThat(pizzaUpdateData.getId()).isEqualTo(pizza.getId());

        BigDecimal actualRatingAverage = pizzaUpdateData.getRatingAverage().stripTrailingZeros();
        BigDecimal expectedRatingAverage = BigDecimal.valueOf(2.5).stripTrailingZeros();
        assertThat(actualRatingAverage).isEqualTo(expectedRatingAverage);
    }
}
