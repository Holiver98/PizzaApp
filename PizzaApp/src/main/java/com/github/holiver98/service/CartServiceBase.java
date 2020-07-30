package com.github.holiver98.service;

import com.github.holiver98.model.*;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class CartServiceBase implements ICartService {
    protected final int cartItemLimit = 15;
    protected List<Pizza> cartContent = new ArrayList<Pizza>();
    protected IUserService userService;
    protected IMailService mailService;

    public CartServiceBase(IUserService userService, IMailService mailService){
        this.userService = userService;
        this.mailService = mailService;
    }

    protected abstract void save(Order order);

    @Override
    public List<Pizza> getCartContent() {
        return cartContent;
    }

    @Override
    public void addPizzaToCart(Pizza pizza) throws CartIsFullException{
        boolean cartIsFull = cartContent.size() >= cartItemLimit;
        if(cartIsFull){
            throw new CartIsFullException("Cart is full, can't add any more pizza!");
        }
        PizzaServiceBase.checkIfPizzaIsValid(pizza);
        cartContent.add(pizza);
    }

    @Override
    public void removePizzaFromCart(Pizza pizza) {
        if(pizza == null){
            throw new NullPointerException("pizza was null");
        }
        cartContent.remove(pizza);
    }

    @Override
    public void placeOrder() throws CartIsEmptyException, NoPermissionException, MessagingException {
        if(cartContent.isEmpty()){
            throw new CartIsEmptyException("Cart is empty, can't place order!");
        }
        User loggedInUser = userService.getLoggedInUser();
        if(loggedInUser == null){
            throw new NoPermissionException("The user has to be logged in to place order!");
        }
        Order order = createOrder(loggedInUser);
        save(order);
        mailService.sendOrderConfirmationEmail(order);
    }

    private Order createOrder(User loggedInUser) {
        Order order = new Order();
        order.setPizzas(cartContent);
        order.setUserEmailAddress(loggedInUser.getEmailAddress());
        order.setTotalPrice(CalculateTotalPrice());
        Date currentTime = new Date();
        order.setDate(currentTime);
        return order;
    }

    protected float CalculateTotalPrice(){
        float totalPrice = 0f;

        for (Pizza pizza: cartContent) {
            totalPrice += pizza.getPrice();
        }

        return totalPrice;
    }
}