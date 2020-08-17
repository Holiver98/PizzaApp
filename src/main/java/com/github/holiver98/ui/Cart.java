package com.github.holiver98.ui;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = "cart")
public class Cart extends VerticalLayout implements View {
    public Cart(){
        VerticalLayout contentVL = new VerticalLayout();
        contentVL.addComponent(new PizzaCartItem());
        contentVL.addComponent(new PizzaCartItem());
        contentVL.addComponent(new PizzaCartItem());
        contentVL.addComponent(new PizzaCartItem());
        contentVL.addComponent(new PizzaCartItem());

        Button orderBtn = new Button("Order");

        addComponent(contentVL);
        addComponent(orderBtn);
    }
}
