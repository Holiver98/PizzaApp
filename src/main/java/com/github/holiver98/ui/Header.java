package com.github.holiver98.ui;

import com.github.holiver98.model.User;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Responsive;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;

import javax.annotation.PostConstruct;
import java.util.Optional;

@UIScope
@SpringView(name = "header")
public class Header extends CssLayout implements View {
    private CssLayout rightContainer;
    private Label usernameLabel = new Label("-");
    private Button registerBtn;
    private Button loginBtn;
    private Button logoutBtn;
    private Button profileBtn;

    public Header(){
        addAttachListener(this::OnAttached);

        setStyleName("header");
        Responsive.makeResponsive(this);
        setWidthFull();

        Button homeBtn = new Button("Home");
        Button customPizzaBtn = new Button("Custom Pizza");
        registerBtn = new Button("Register");
        loginBtn = new Button("Login");
        logoutBtn = new Button("Logout");
        profileBtn = new Button("Profile");
        Button cartBtn = new Button();
        cartBtn.setIcon(VaadinIcons.CART);

        CssLayout leftContainer = new CssLayout();
        leftContainer.setStyleName("leftcontainer");
        leftContainer.addComponent(homeBtn);
        leftContainer.addComponent(customPizzaBtn);

        rightContainer = new CssLayout();
        rightContainer.setStyleName("rightcontainer");
        rightContainer.addComponent(registerBtn);
        rightContainer.addComponent(loginBtn);
        rightContainer.addComponent(cartBtn);

        addComponent(leftContainer);
        addComponent(rightContainer);

        profileBtn.addClickListener(clickEvent -> goToProfile());
        homeBtn.addClickListener(clickEvent -> getUI().getNavigator().navigateTo(""));
        loginBtn.addClickListener(clickEvent -> getUI().getNavigator().navigateTo("login"));
        registerBtn.addClickListener(clickEvent -> getUI().getNavigator().navigateTo("register"));
        customPizzaBtn.addClickListener(clickEvent -> getUI().getNavigator().navigateTo("custom"));
        cartBtn.addClickListener(clickEvent -> getUI().getNavigator().navigateTo("cart"));
    }

    @PostConstruct
    public void afterInit(){
        setLogoutBtnClickListener();
    }

    public void OnAttached(AttachEvent attachEvent){
        updateHeaderIfUserIsLoggedIn();
    }

    private void goToProfile() {
        getUI().getNavigator().navigateTo("profile");
    }

    private void setLogoutBtnClickListener(){
        logoutBtn.addClickListener(clickEvent -> logout());
    }

    private void updateHeaderIfUserIsLoggedIn(){
        Optional<User> loggedInUser = ((MainView)getUI()).getLoggedInUser();
        loggedInUser.ifPresent(u -> login(u.getUsername()));
    }

    public void login(String username) {
        usernameLabel.setValue("Hello, " + username + "!");
        rightContainer.removeComponent(registerBtn);
        rightContainer.removeComponent(loginBtn);
        rightContainer.addComponent(usernameLabel, 0);
        rightContainer.addComponent(profileBtn, 1);
        rightContainer.addComponent(logoutBtn, 2);
    }

    private void logout() {
        MainView.AuthenticationResult result = ((MainView)getUI()).logout();
        if(result.equals(MainView.AuthenticationResult.FAILURE)){
            return;
        }

        usernameLabel.setValue("-");
        rightContainer.removeComponent(usernameLabel);
        rightContainer.removeComponent(profileBtn);
        rightContainer.removeComponent(logoutBtn);
        rightContainer.addComponent(registerBtn, 0);
        rightContainer.addComponent(loginBtn, 1);
        getUI().getNavigator().navigateTo("");
        Notification.show("Successfully logged out", Notification.Type.WARNING_MESSAGE);
    }
}
