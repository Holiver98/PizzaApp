package com.github.holiver98.ui;

import com.github.holiver98.model.User;
import com.github.holiver98.service.IUserService;
import com.github.holiver98.service.IUserServiceListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.util.Optional;

@UIScope
@SpringView(name = "header")
public class Header extends CssLayout implements View, IUserServiceListener {
    @Autowired
    private IUserService userService;

    private CssLayout rightContainer;
    private Label usernameLabel = new Label("-");
    private Button registerBtn;
    private Button loginBtn;
    private Button logoutBtn;

    public Header(){
        setStyleName("header");
        Responsive.makeResponsive(this);
        setWidthFull();

        Label logo = new Label("I hate " + VaadinIcons.VAADIN_H.getHtml() + " very much!",
                ContentMode.HTML);
        Button homeBtn = new Button("Home");
        Button customPizzaBtn = new Button("Custom Pizza");
        registerBtn = new Button("Register");
        loginBtn = new Button("Login");
        logoutBtn = new Button("Logout");
        Button cartBtn = new Button();
        cartBtn.setIcon(VaadinIcons.CART);

        CssLayout leftContainer = new CssLayout();
        leftContainer.setStyleName("leftcontainer");
        leftContainer.addComponent(logo);
        leftContainer.addComponent(homeBtn);
        leftContainer.addComponent(customPizzaBtn);

        rightContainer = new CssLayout();
        rightContainer.setStyleName("rightcontainer");
        rightContainer.addComponent(registerBtn);
        rightContainer.addComponent(loginBtn);
        rightContainer.addComponent(cartBtn);

        addComponent(leftContainer);
        addComponent(rightContainer);

        homeBtn.addClickListener(clickEvent -> getUI().getNavigator().navigateTo(""));
        loginBtn.addClickListener(clickEvent -> getUI().getNavigator().navigateTo("login"));
        registerBtn.addClickListener(clickEvent -> getUI().getNavigator().navigateTo("register"));
        customPizzaBtn.addClickListener(clickEvent -> getUI().getNavigator().navigateTo("custom"));
        cartBtn.addClickListener(clickEvent -> getUI().getNavigator().navigateTo("cart"));
    }

    @PostConstruct
    public void afterInit(){
        userService.addListener(this);
        setLogoutBtnClickListener();
        updateHeaderIfUserIsLoggedIn();
    }

    private void setLogoutBtnClickListener(){
        logoutBtn.addClickListener(clickEvent -> {
            userService.logout();
        });
    }

    private void updateHeaderIfUserIsLoggedIn(){
        Optional<User> loggedInUser = userService.getLoggedInUser();
        loggedInUser.ifPresent(u -> login(u.getUsername()));
    }

    public void login(String username) {
        usernameLabel.setValue(username);
        rightContainer.removeComponent(registerBtn);
        rightContainer.removeComponent(loginBtn);
        rightContainer.addComponent(usernameLabel, 0);
        rightContainer.addComponent(logoutBtn, 1);
    }

    public void logout() {
        usernameLabel.setValue("-");
        rightContainer.removeComponent(usernameLabel);
        rightContainer.removeComponent(logoutBtn);
        rightContainer.addComponent(registerBtn, 0);
        rightContainer.addComponent(loginBtn, 1);
    }

    @Override
    public void OnLoggedIn(User user) {
        login(user.getUsername());
    }

    @Override
    public void OnLoggedOut() {
        logout();
    }
}
