package com.github.holiver98.service;

import com.github.holiver98.dao.IInMemoryOrderDao;
import com.github.holiver98.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InMemoryCartService implements ICartService{
    private final int cartItemLimit = 15;
    private final int minIngredients = 1;
    private final int maxIngredients = 5;
    private final int maxBaseSauce = 1;
    private final int minBaseSauce = 1;
    private List<Pizza> cartContent = new ArrayList<Pizza>();
    private IUserService userService;
    private IInMemoryOrderDao orderDao;
    private IMailService mailService;

    public InMemoryCartService(IUserService userService, IInMemoryOrderDao orderDao, IMailService mailService){
        this.userService = userService;
        this.orderDao = orderDao;
        this.mailService = mailService;
    }

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
        orderDao.saveOrder(order);

        mailService.sendOrderConfirmationEmail(order);
    }

    private boolean isValidPizza(Pizza pizza){
        if(pizza.getName() == null || pizza.getIngredients() == null ||
        pizza.getSize() == null || pizza.getPrice() <= 0.0f){
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

    private boolean hasMoreOrLessBaseSauceThanNeeded(Pizza pizza){
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

    private float CalculateTotalPrice(){
        float totalPrice = 0f;

        for (Pizza p: cartContent) {
            totalPrice += p.getPrice();
        }

        return totalPrice;
    }
}
