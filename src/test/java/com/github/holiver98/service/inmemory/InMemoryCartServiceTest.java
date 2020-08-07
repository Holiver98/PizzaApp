package com.github.holiver98.service.inmemory;

import com.github.holiver98.dal.inmemory.IInMemoryOrderDao;
import com.github.holiver98.model.*;
import com.github.holiver98.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class InMemoryCartServiceTest extends InMemoryCartServiceTestBase {

    private IInMemoryOrderDao orderDao;
    private IMailService mailService;
    private IUserService userService;

    private InMemoryCartService cartService;

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @BeforeEach
    void init(){
        orderDao = Mockito.mock(IInMemoryOrderDao.class);
        mailService = Mockito.mock(IMailService.class);
        userService = Mockito.mock(IUserService.class);
        cartService = new InMemoryCartService(userService, orderDao, mailService);
    }

    @Test
    void addPizzaToCart_Adding_Valid_Pizza_To_Empty_Cart_Should_Add_Pizza() throws CartIsFullException {
        //Arrange
        Pizza validPizza = createValidPizza("pepperoni", 1L);

        //Act
        cartService.addPizzaToCart(validPizza);

        //Assert
        assertThat(cartService.getCartContent().size()).isEqualTo(1);
        assertThat(cartService.getCartContent().get(0)).isEqualTo(validPizza);
    }

    @Test
    void addPizzaToCart_Adding_Valid_Pizza_To_Full_Cart_Should_Throw_Exception(){
        //Arrange
        Pizza validPizza = createValidPizza("pepperoni", 1L);
        for(int i=0; i<15; i++){
            cartService.getCartContent().add(validPizza);
        }

        Pizza anotherValidPizza = createValidPizza("Bestpizza", 2L);

        //Act
        //Assert
        Assertions.assertThrows(CartIsFullException.class,
                () -> cartService.addPizzaToCart(anotherValidPizza));
        assertThat(cartService.getCartContent().size()).isEqualTo(15);
        assertThat(cartService.getCartContent().contains(anotherValidPizza)).isEqualTo(false);
    }

    @Test
    void addPizzaToCart_Adding_Pizza_With_Two_Basesauce_Should_Throw_Exception(){
        //Arrange
        Pizza pizza = createPizzaWithTwoBaseSauce();

        //Act
        //Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> cartService.addPizzaToCart(pizza));
        assertThat(cartService.getCartContent().size()).isEqualTo(0);
    }

    @Test
    void addPizzaToCart_Adding_Pizza_With_Missing_Information_Should_Throw_Exception(){
        //Arrange
        Pizza pizza = new Pizza();

        //Act
        //Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> cartService.addPizzaToCart(pizza));
        assertThat(cartService.getCartContent().size()).isEqualTo(0);
    }

    @Test
    void addPizzaToCart_Passing_Null_Should_Throw_Exception(){
        //Arrange
        //Act
        //Assert
        Assertions.assertThrows(NullPointerException.class,
                () -> cartService.addPizzaToCart(null));
        assertThat(cartService.getCartContent().size()).isEqualTo(0);
    }

    @Test
    void removePizzaFromCart_Cart_Is_Empty_Should_Not_Do_Anything(){
        //Arrange
        Pizza pizza = createValidPizza("pepperoni pizza", 1L);

        //Act
        cartService.removePizzaFromCart(pizza);

        //Assert
        assertThat(cartService.getCartContent().size()).isEqualTo(0);
    }

    @Test
    void removePizzaFromCart_Duplicate_Pizzas_In_Cart_Should_Only_Remove_One(){
        //Arrange
        Pizza pizza = createValidPizza("pepperoni pizza", 1L);
        cartService.getCartContent().add(pizza);
        cartService.getCartContent().add(pizza);
        cartService.getCartContent().add(pizza);

        //Act
        cartService.removePizzaFromCart(pizza);

        //Assert
        assertThat(cartService.getCartContent().size()).isEqualTo(2);
    }

    @Test
    void removePizzaFromCart_No_Such_Pizza_In_Cart_Should_Not_Remove(){
        //Arrange
        Pizza pizza = createValidPizza("pepperoni pizza", 1L);
        cartService.getCartContent().add(pizza);
        cartService.getCartContent().add(pizza);
        cartService.getCartContent().add(pizza);

        Pizza differentPizza = createValidPizza("Cheese pizza", 2L);

        //Act
        cartService.removePizzaFromCart(differentPizza);

        //Assert
        assertThat(cartService.getCartContent().size()).isEqualTo(3);
    }

    @Test
    void removePizzaFromCart_Passing_Null_Should_Throw_Exception(){
        //Arrange
        Pizza pizza = createValidPizza("pepperoni pizza", 1L);
        cartService.getCartContent().add(pizza);
        cartService.getCartContent().add(pizza);
        cartService.getCartContent().add(pizza);

        //Act
        //Assert
        Assertions.assertThrows(NullPointerException.class,
                () -> cartService.removePizzaFromCart(null));
        assertThat(cartService.getCartContent().size()).isEqualTo(3);
    }

    @Test
    void placeOrder_Placing_Order_With_3_Pizzas_Should_Save_Order_And_Send_Email() throws CartIsEmptyException, MessagingException {
        //Arrange
        Pizza validPizza = createValidPizza("Pepperoni pizza", 1L);
        cartService.getCartContent().add(validPizza);
        cartService.getCartContent().add(validPizza);
        cartService.getCartContent().add(validPizza);

        User loggedInUser = new User();
        loggedInUser.setEmailAddress("test123@mmm.hu");
        loggedInUser.setUsername("Bobby");
        loggedInUser.setPassword("asdassa123");
        Mockito.when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        //Act
        cartService.placeOrder();

        //Assert
        Mockito.verify(orderDao).saveOrder(orderArgumentCaptor.capture());
        Order savedOrder = orderArgumentCaptor.getValue();
        Mockito.verify(mailService, Mockito.times(1)).sendOrderConfirmationEmail(savedOrder);
        Mockito.verify(orderDao, Mockito.times(1)).saveOrder(Mockito.any());
    }

    @Test
    void placeOrder_While_Being_Logged_Out_Should_Throw_Exception() throws MessagingException {
        //Arrange
        Pizza validPizza = createValidPizza("Pepperoni pizza", 1L);
        cartService.getCartContent().add(validPizza);
        cartService.getCartContent().add(validPizza);
        cartService.getCartContent().add(validPizza);

        Mockito.when(userService.getLoggedInUser()).thenReturn(null);

        //Act
        //Assert
        Assertions.assertThrows(NoPermissionException.class,
                () -> cartService.placeOrder());
        Mockito.verify(mailService, Mockito.times(0)).sendMailTo(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(orderDao, Mockito.times(0)).saveOrder(Mockito.any());
    }

    @Test
    void placeOrder_With_Empty_Cart_Should_Throw_Exception() throws MessagingException {
        //Arrange
        User loggedInUser = new User();
        loggedInUser.setEmailAddress("test123@mmm.hu");
        loggedInUser.setUsername("Bobby");
        loggedInUser.setPassword("asdassa123");
        Mockito.lenient().when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        //Act
        //Assert
        Assertions.assertThrows(CartIsEmptyException.class,
                () -> cartService.placeOrder());
        Mockito.verify(mailService, Mockito.times(0)).sendMailTo(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(orderDao, Mockito.times(0)).saveOrder(Mockito.any());
    }

    @Test
    void placeOrder_Placing_Valid_Order_Should_Save_Correct_Order_Information() throws CartIsEmptyException, MessagingException {
        //Arrange
        Pizza pepperoniPizza = createValidPizza("Pepperoni pizza", 1L);
        cartService.getCartContent().add(pepperoniPizza);
        Pizza cheesePizza = createValidPizza("Cheese pizza", 2L);
        cartService.getCartContent().add(cheesePizza);

        User loggedInUser = new User();
        loggedInUser.setEmailAddress("test123@mmm.hu");
        loggedInUser.setUsername("Bobby");
        loggedInUser.setPassword("asdassa123");
        Mockito.when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        //Act
        cartService.placeOrder();

        //Assert
        Mockito.verify(orderDao).saveOrder(orderArgumentCaptor.capture());
        Order savedOrder = orderArgumentCaptor.getValue();

        assertThat(savedOrder.getUserEmailAddress()).isEqualTo(loggedInUser.getEmailAddress());
        assertThat(savedOrder.getPizzas().size()).isEqualTo(2);
        assertThat(savedOrder.getPizzas().contains(pepperoniPizza)).isTrue();
        assertThat(savedOrder.getPizzas().contains(cheesePizza)).isTrue();
        BigDecimal expectedTotalPrice = pepperoniPizza.getPrice().add(cheesePizza.getPrice());
        assertThat(savedOrder.getTotalPrice()).isEqualTo(expectedTotalPrice);
        Date currentTime = new Date();
        assertThat(savedOrder.getDate()).isBeforeOrEqualTo(currentTime);
    }
}
