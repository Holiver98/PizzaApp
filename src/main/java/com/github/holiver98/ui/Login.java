package com.github.holiver98.ui;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = "login")
public class Login extends VerticalLayout implements View {
    public Login(){
        addComponent(new Label("this is the login page"));
    }
}
