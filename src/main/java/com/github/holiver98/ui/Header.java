package com.github.holiver98.ui;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

@SpringView
public class Header extends CssLayout implements View {
    public Header(){
        setStyleName("header");
        Responsive.makeResponsive(this);
        setWidthFull();

        Label logo = new Label("I hate " + VaadinIcons.VAADIN_H.getHtml() + " very much!",
                ContentMode.HTML);
        Button homeBtn = new Button("Home");
        Button customPizzaBtn = new Button("Custom Pizza");
        Button registerBtn = new Button("Register");
        Button loginBtn = new Button("Login");
        Button cartBtn = new Button();
        cartBtn.setIcon(VaadinIcons.CART);

        CssLayout leftContainer = new CssLayout();
        leftContainer.setStyleName("leftcontainer");
        leftContainer.addComponent(logo);
        leftContainer.addComponent(homeBtn);
        leftContainer.addComponent(customPizzaBtn);

        CssLayout rightContainer = new CssLayout();
        rightContainer.setStyleName("rightcontainer");
        rightContainer.addComponent(registerBtn);
        rightContainer.addComponent(loginBtn);
        rightContainer.addComponent(cartBtn);

        addComponent(leftContainer);
        addComponent(rightContainer);

        homeBtn.addClickListener(clickEvent -> getUI().getNavigator().navigateTo(""));
        loginBtn.addClickListener(clickEvent -> getUI().getNavigator().navigateTo("login"));
    }
}
