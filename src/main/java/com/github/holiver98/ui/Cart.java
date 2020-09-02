package com.github.holiver98.ui;

import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.User;
import com.github.holiver98.service.ICartService;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Optional;

@SpringView(name = "cart")
public class Cart extends VerticalLayout implements View {
    @Autowired
    private ICartService cartService;

    private VerticalLayout contentVL;

    public Cart(){
        contentVL = new VerticalLayout();
        contentVL.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Button orderBtn = new Button("Order");
        orderBtn.setHeight("50px");
        orderBtn.setWidth("100px");

        addComponent(contentVL);
        addComponent(orderBtn);

        setComponentAlignment(orderBtn, Alignment.MIDDLE_CENTER);


        orderBtn.addClickListener(clickEvent -> goToOrderView());
    }

    @PostConstruct
    public void afterInit(){
        for(Pizza pizza : cartService.getCartContent()){
            if(pizza.isCustom()){
                continue;
            }
            PizzaCartItem pizzaUi = new PizzaCartItem(pizza);
            pizzaUi.getRemoveBtn().addClickListener(clickEvent -> {
                cartService.removePizzaFromCart(pizza);
                contentVL.removeComponent(pizzaUi);
            });
            contentVL.addComponent(pizzaUi);
        }

        for(Pizza pizza : cartService.getCartContent()){
            if(!pizza.isCustom()){
                continue;
            }
            CustomPizzaCartItem pizzaUi = new CustomPizzaCartItem(pizza);
            pizzaUi.getRemoveBtn().addClickListener(clickEvent -> {
                cartService.removePizzaFromCart(pizza);
                contentVL.removeComponent(pizzaUi);
            });
            contentVL.addComponent(pizzaUi);
        }
    }

    private void goToOrderView(){
        if(cartService.getCartContent().size() == 0){
            Notification.show("The cart is empty.", Notification.Type.WARNING_MESSAGE);
            return;
        }

        Optional<User> loggedInUser = ((MainView)getUI()).getLoggedInUser();
        if(loggedInUser.isPresent()){
            getUI().getNavigator().navigateTo("order");
        }else{
            getUI().getNavigator().navigateTo("login");
            Notification.show("You need to be logged in!", Notification.Type.WARNING_MESSAGE);
        }
    }
}
