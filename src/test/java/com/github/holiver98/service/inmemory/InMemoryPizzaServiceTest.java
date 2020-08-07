package com.github.holiver98.service.inmemory;

import com.github.holiver98.dal.inmemory.IInMemoryIngredientDao;
import com.github.holiver98.dal.inmemory.IInMemoryPizzaDao;
import com.github.holiver98.dal.inmemory.IInMemoryRatingDao;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.Rating;
import com.github.holiver98.service.IUserService;
import com.github.holiver98.service.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class InMemoryPizzaServiceTest extends InMemoryPizzaServiceTestBase {
    private IInMemoryIngredientDao ingredientDao;
    private IInMemoryPizzaDao pizzaDao;
    private IUserService userService;

    private InMemoryPizzaService pizzaService;

    @BeforeEach
    void init(){
        ingredientDao = Mockito.mock(IInMemoryIngredientDao.class);
        pizzaDao = Mockito.mock(IInMemoryPizzaDao.class);
        userService = Mockito.mock(IUserService.class);
        pizzaService = new InMemoryPizzaService(pizzaDao, ingredientDao, userService);
    }

    @Test
    void calculatePrice_Given_3_Ingredients_With_Valid_Prices(){
        //Arrange
        Pizza pizza = createPizzaWith3IngredientsGivenThePrices(BigDecimal.valueOf(8.2), BigDecimal.valueOf(2.0), BigDecimal.valueOf(3.6));

        //Act
        BigDecimal price = pizzaService.calculatePrice(pizza);

        //Assert
        assertThat(price).isEqualTo(BigDecimal.valueOf(13.8));
    }

    @Test
    void calculatePrice_Given_3_Ingredients_Some_Have_Invalid_Price_Should_Throw_Exception(){
        //Arrange
        Pizza pizza = createPizzaWith3IngredientsGivenThePrices(BigDecimal.valueOf(-2.1), BigDecimal.valueOf(-3.3), BigDecimal.valueOf(8.4));

        //Act
        //Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> pizzaService.calculatePrice(pizza));
    }

    @Test
    void calculatePrice_Given_3_Ingredients_Some_Have_Invalid_Price_Should_Throw_Exception_2(){
        //Arrange
        Pizza pizza = createPizzaWith3IngredientsGivenThePrices(BigDecimal.valueOf(-10.1), BigDecimal.valueOf(-3.3), BigDecimal.valueOf(8.4));

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
}
