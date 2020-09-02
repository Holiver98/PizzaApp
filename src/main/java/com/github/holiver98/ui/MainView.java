package com.github.holiver98.ui;

import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.Role;
import com.github.holiver98.model.User;
import com.github.holiver98.service.*;
import com.github.holiver98.util.RequiresAuthentication;
import com.github.holiver98.util.RequiresRole;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Theme("mytheme")
@Title("PizzaApp")
@SpringUI
@SpringViewDisplay
public class MainView extends UI implements ViewDisplay {
    public enum AuthenticationResult{
        SUCCESS,
        FAILURE
    }

    @Autowired
    private IUserService userService;
    @Autowired
    private Header header;
    private Panel body = new Panel();
    private Footer footer;
    @Autowired
    private SpringNavigator navigator;

    @Value("${pizzaApp.cart.itemLimit}")
    private int cartItemLimit;

    private User loggedInUser;
    private List<Pizza> cartContent = new ArrayList<Pizza>();

    //ez később hívódik meg, mint a @PostConstruct
    @Override
    protected void init(VaadinRequest request){
        User user = (User) getSession().getAttribute("loggedInUser");
        if(user != null){
            loggedInUser = user;
        }

        List<Pizza> sessionCartContent = (List<Pizza>) getSession().getAttribute("cartContent");
        if(sessionCartContent != null){
            cartContent = sessionCartContent;
        }else{
            getSession().setAttribute("cartContent", cartContent);
        }

        VerticalLayout content = new VerticalLayout();
        content.setStyleName("main");
        setContent(content);

        content.addComponent(header);
        content.addComponent(body);

        footer = new Footer();
        content.addComponent(footer);
        
        navigator.addViewChangeListener(this::beforeViewChange);
    }

    private boolean beforeViewChange(ViewChangeListener.ViewChangeEvent event) {
        Annotation requiresAuthenticationAnnotation = AnnotationUtils.findAnnotation(event.getNewView().getClass(), RequiresAuthentication.class);
        if(requiresAuthenticationAnnotation != null){
            if(loggedInUser == null){
                Notification.show("You need to be logged in.", Notification.Type.WARNING_MESSAGE);
                return false;
            }

            if(event.getNewView().getClass().isAnnotationPresent(RequiresRole.class)){
                Role requiredRole = event.getNewView().getClass().getAnnotation(RequiresRole.class).role();
                if(!loggedInUser.getRole().equals(requiredRole)){
                    Notification.show("You need to have " + requiredRole.toString() + " role.", Notification.Type.WARNING_MESSAGE);
                    return false;
                }
            }
        }

        return true;
    }


    @Override
    public void showView(View view) {
        body.setContent(view.getViewComponent());
    }

    public Optional<User> getLoggedInUser(){
        return Optional.ofNullable(loggedInUser);
    }

    public AuthenticationResult login(String emailAddress, String password){
        User user;
        try {
            user = userService.login(emailAddress, password);
        } catch (IncorrectPasswordException | NotFoundException e) {
            Notification.show("Invalid email or password.", Notification.Type.WARNING_MESSAGE);
            return AuthenticationResult.FAILURE;
        }

        loggedInUser = user;
        getSession().setAttribute("loggedInUser", user);
        header.login(user.getUsername());
        getNavigator().navigateTo("");
        return AuthenticationResult.SUCCESS;
    }

    public AuthenticationResult logout(){
        if(loggedInUser == null){
            throw new UnsupportedOperationException("Need to be logged in to logout!");
        }

        loggedInUser = null;
        getSession().setAttribute("loggedInUser", null);
        return AuthenticationResult.SUCCESS;
    }

    /**
     * @return A copy of the cart content.
     */
    public List<Pizza> getCartContent() {
        return new ArrayList<>(cartContent);
    }

    public void clearCartContent(){
        cartContent.clear();
    }

    public void addPizzaToCart(Pizza pizza) throws CartIsFullException {
        boolean cartIsFull = cartContent.size() >= cartItemLimit;
        if(cartIsFull){
            throw new CartIsFullException("Cart is full, can't add any more pizza!");
        }
        PizzaServiceBase.checkIfPizzaIsValid(pizza);
        cartContent.add(pizza);
    }

    public void removePizzaFromCart(Pizza pizza){
        if(pizza == null){
            throw new NullPointerException("pizza was null");
        }
        cartContent.remove(pizza);
    }
}
