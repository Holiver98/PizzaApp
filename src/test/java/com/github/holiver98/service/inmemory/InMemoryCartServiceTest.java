package com.github.holiver98.service.inmemory;

import com.github.holiver98.dal.inmemory.IInMemoryOrderDao;
import com.github.holiver98.model.Order;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.User;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
    void placeOrder_Placing_Order_With_3_Pizzas_Should_Save_Order_And_Send_Email() throws CartIsEmptyException, MessagingException, NotFoundException {
        //Arrange
        Pizza validPizza = createValidPizza("Pepperoni pizza", 1L);
        List<Pizza> cartContent = new ArrayList<>();
        cartContent.add(validPizza);
        cartContent.add(validPizza);
        cartContent.add(validPizza);

        User loggedInUser = new User();
        loggedInUser.setEmailAddress("test123@mmm.hu");
        loggedInUser.setUsername("Bobby");
        loggedInUser.setPassword("asdassa123");
        Mockito.when(userService.getUser(loggedInUser.getEmailAddress())).thenReturn(Optional.of(loggedInUser));

        //Act
        cartService.placeOrder(loggedInUser.getEmailAddress(), cartContent);

        //Assert
        Mockito.verify(orderDao).saveOrder(orderArgumentCaptor.capture());
        Order savedOrder = orderArgumentCaptor.getValue();
        Mockito.verify(mailService, Mockito.times(1)).sendOrderConfirmationEmail(savedOrder);
        Mockito.verify(orderDao, Mockito.times(1)).saveOrder(Mockito.any());
    }

    @Test
    void placeOrder_With_Empty_Cart_Should_Throw_Exception() throws MessagingException, NotFoundException {
        //Arrange
        List<Pizza> cartContent = new ArrayList<>();

        User loggedInUser = new User();
        loggedInUser.setEmailAddress("test123@mmm.hu");
        loggedInUser.setUsername("Bobby");
        loggedInUser.setPassword("asdassa123");
        Mockito.lenient().when(userService.getUser(loggedInUser.getEmailAddress())).thenReturn(Optional.of(loggedInUser));

        //Act
        //Assert
        Assertions.assertThrows(CartIsEmptyException.class,
                () -> cartService.placeOrder(loggedInUser.getEmailAddress(), cartContent));
        Mockito.verify(mailService, Mockito.times(0)).sendMailTo(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(orderDao, Mockito.times(0)).saveOrder(Mockito.any());
    }

    @Test
    void placeOrder_Placing_Valid_Order_Should_Save_Correct_Order_Information() throws CartIsEmptyException, MessagingException, NotFoundException {
        //Arrange
        List<Pizza> cartContent = new ArrayList<>();
        Pizza pepperoniPizza = createValidPizza("Pepperoni pizza", 1L);
        cartContent.add(pepperoniPizza);
        Pizza cheesePizza = createValidPizza("Cheese pizza", 2L);
        cartContent.add(cheesePizza);

        User loggedInUser = new User();
        loggedInUser.setEmailAddress("test123@mmm.hu");
        loggedInUser.setUsername("Bobby");
        loggedInUser.setPassword("asdassa123");
        Mockito.when(userService.getUser(loggedInUser.getEmailAddress())).thenReturn(Optional.of(loggedInUser));

        //Act
        cartService.placeOrder(loggedInUser.getEmailAddress(), cartContent);

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
