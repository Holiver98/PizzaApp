package com.github.holiver98.service;

import com.github.holiver98.model.Order;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.User;
import org.springframework.beans.factory.annotation.Value;
import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class CartServiceBase implements ICartService {
    @Value("${pizzaApp.cart.itemLimit}")
    protected int cartItemLimit;
    protected IUserService userService;
    protected IMailService mailService;

    public CartServiceBase(IUserService userService, IMailService mailService){
        this.userService = userService;
        this.mailService = mailService;
    }

    protected abstract void save(Order order);

    @Override
    public void placeOrder(String emailAddress, List<Pizza> cartContent) throws CartIsEmptyException, NoPermissionException, MessagingException{
        if(emailAddress == null){
            throw new NullPointerException("emailAddress was null");
        }
        if(cartContent.isEmpty()){
            throw new CartIsEmptyException("Cart is empty, can't place order!");
        }

        User user = userService.getUser(emailAddress)
                .orElseThrow(() -> new NotRegisteredException("No user is registered with this email address: " + emailAddress));
        Order order = createOrder(user, cartContent);
        save(order);
        mailService.sendOrderConfirmationEmail(order);
    }

    private Order createOrder(User loggedInUser, List<Pizza> cartContent) {
        Order order = new Order();
        order.setPizzas(cartContent);
        order.setUserEmailAddress(loggedInUser.getEmailAddress());
        order.setTotalPrice(CalculateTotalPrice(cartContent));
        Date currentTime = new Date();
        order.setDate(currentTime);
        return order;
    }

    protected BigDecimal CalculateTotalPrice(List<Pizza> cartContent){
        BigDecimal totalPrice = BigDecimal.valueOf(0);

        for (Pizza pizza: cartContent) {
            totalPrice = totalPrice.add(pizza.getPrice());
        }

        return totalPrice;
    }
}
