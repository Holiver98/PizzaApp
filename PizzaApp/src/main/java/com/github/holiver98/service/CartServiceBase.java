package com.github.holiver98.service;

import com.github.holiver98.model.*;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class CartServiceBase implements ICartService {
    protected final int cartItemLimit = 15;
    protected final int minIngredients = 1;
    protected final int maxIngredients = 5;
    protected final int maxBaseSauce = 1;
    protected final int minBaseSauce = 1;
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
        if(pizza == null){
            return;
        }else if(cartContent.size() >= cartItemLimit){
            System.out.println("Cart is full!");
            return;
        }if(!isValidPizza(pizza)){
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
        if(cartContent.isEmpty()){
            return;
        }

        User loggedInUser = userService.getLoggedInUser();
        if(loggedInUser == null){
            return;
        }

        Order order = new Order();
        order.setPizzas(cartContent);
        order.setUserEmailAddress(loggedInUser.getEmailAddress());
        order.setTotalPrice(CalculateTotalPrice());
        Date currentTime = new Date();
        order.setDate(currentTime);
        save(order);

        try {
            mailService.sendOrderConfirmationEmail(order);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    protected boolean isValidPizza(Pizza pizza){
        if(pizza.getName() == null || pizza.getIngredients() == null ||
                pizza.getSize() == null || pizza.getPrice() <= 0.0f || pizza.getId() == null){
            return false;
        }else if(pizza.getIngredients().size() < minIngredients || pizza.getIngredients().size() > maxIngredients){
            return false;
        }

        if(hasMoreOrLessBaseSauceThanNeeded(pizza)){
            System.out.println("There has to be exactly 1 base sauce!");
            return false;
        }

        return true;
    }

    protected boolean hasMoreOrLessBaseSauceThanNeeded(Pizza pizza){
        int numberOfBaseSauces = 0;
        for (Ingredient i: pizza.getIngredients()) {
            if(i.getType().equals(IngredientType.PIZZA_BASESAUCE)){
                numberOfBaseSauces++;
            }
        }

        if(numberOfBaseSauces > maxBaseSauce || numberOfBaseSauces < minBaseSauce){
            return true;
        }else{
            return false;
        }
    }

    protected float CalculateTotalPrice(){
        float totalPrice = 0f;

        for (Pizza p: cartContent) {
            totalPrice += p.getPrice();
        }

        return totalPrice;
    }
}
