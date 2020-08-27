package com.github.holiver98.ui;

import com.github.holiver98.model.User;
import com.github.holiver98.service.IUserService;
import com.github.holiver98.service.IncorrectPasswordException;
import com.github.holiver98.service.NotFoundException;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

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

    private User loggedInUser;

    //ez később hívódik meg, mint a @PostConstruct
    @Override
    protected void init(VaadinRequest request){
        VerticalLayout content = new VerticalLayout();
        content.setStyleName("main");
        setContent(content);

        content.addComponent(header);

        content.addComponent(body);

        footer = new Footer();
        content.addComponent(footer);
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
        header.login(user.getUsername());
        return AuthenticationResult.SUCCESS;
    }

    public AuthenticationResult logout(){
        if(loggedInUser == null){
            throw new UnsupportedOperationException("Need to be logged in to logout!");
        }

        int result = userService.logout(loggedInUser.getEmailAddress());

        if(result == 0){
            return AuthenticationResult.FAILURE;
        }else{
            loggedInUser = null;
            return AuthenticationResult.SUCCESS;
        }
    }
}
