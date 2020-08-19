package com.github.holiver98.ui;

import com.github.holiver98.model.Pizza;
import com.github.holiver98.service.ICartService;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;

@SpringView(name = "cart")
public class Cart extends VerticalLayout implements View {
    @Autowired
    private ICartService cartService;

    private VerticalLayout contentVL;

    public Cart(){
        contentVL = new VerticalLayout();

        Button orderBtn = new Button("Order");

        addComponent(contentVL);
        addComponent(orderBtn);
    }

    @PostConstruct
    public void afterInit(){
        for(Pizza pizza : cartService.getCartContent()){
            PizzaCartItem pizzaUi = new PizzaCartItem(pizza);
            pizzaUi.getRemoveBtn().addClickListener(clickEvent -> {
                cartService.removePizzaFromCart(pizza);
                contentVL.removeComponent(pizzaUi);
            });
            contentVL.addComponent(pizzaUi);
        }
    }
}
