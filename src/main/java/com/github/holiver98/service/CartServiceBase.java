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
    @Value("${pizzaapp.cart.itemlimit}")
    protected int cartItemLimit;
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
    public void placeOrder(String emailAddress) throws CartIsEmptyException, NoPermissionException, MessagingException{
        if(emailAddress == null){
            throw new NullPointerException("emailAddress was null");
        }
        if(cartContent.isEmpty()){
            throw new CartIsEmptyException("Cart is empty, can't place order!");
        }

        User user = userService.getUser(emailAddress)
                .orElseThrow(() -> new NotRegisteredException("No user is registered with this email address: " + emailAddress));
        Order order = createOrder(user);
        save(order);
        mailService.sendOrderConfirmationEmail(order);
    }

    @Override
    public void clearContent(){
        cartContent.clear();
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

    protected BigDecimal CalculateTotalPrice(){
        BigDecimal totalPrice = BigDecimal.valueOf(0);

        for (Pizza pizza: cartContent) {
            totalPrice = totalPrice.add(pizza.getPrice());
        }

        return totalPrice;
    }
}
