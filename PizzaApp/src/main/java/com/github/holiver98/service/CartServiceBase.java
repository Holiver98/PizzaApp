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
    public void addPizzaToCart(Pizza pizza) {
        boolean cartIsFull = cartContent.size() >= cartItemLimit;
        if(cartIsFull){
            System.out.println("Cart is full!");
            return;
        }
        if(!PizzaServiceBase.isValidPizza(pizza)){
            System.out.println("Invalid pizza!");
            return;
        }
        cartContent.add(pizza);
    }

    @Override
    public void removePizzaFromCart(Pizza pizza) {
        if(pizza == null){
            return;
        }
        cartContent.remove(pizza);
    }

    @Override
    public void placeOrder() {
        boolean cartIsEmpty = cartContent.isEmpty();
        if(cartIsEmpty){
            return;
        }
        User loggedInUser = userService.getLoggedInUser();
        if(loggedInUser == null){
            return;
        }
        Order order = createOrder(loggedInUser);
        save(order);
        trySendOrderConfirmationEmail(order);
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

    private void trySendOrderConfirmationEmail(Order order) {
        try {
            mailService.sendOrderConfirmationEmail(order);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    protected float CalculateTotalPrice(){
        float totalPrice = 0f;

        for (Pizza pizza: cartContent) {
            totalPrice += pizza.getPrice();
        }

        return totalPrice;
    }
}
