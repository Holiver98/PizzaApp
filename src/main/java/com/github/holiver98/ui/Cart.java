package com.github.holiver98.ui;

import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.User;
import com.github.holiver98.service.ICartService;
import com.github.holiver98.service.IUserService;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
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
    @Autowired
    private IUserService userService;

    private VerticalLayout contentVL;

    public Cart(){
        contentVL = new VerticalLayout();

        Button orderBtn = new Button("Order");

        addComponent(contentVL);
        addComponent(orderBtn);

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
        Optional<User> loggedInUser = userService.getLoggedInUser();
        if(loggedInUser.isPresent()){
            getUI().getNavigator().navigateTo("order");
        }else{
            Notification.show("You need to be logged in!", Notification.Type.WARNING_MESSAGE);
        }
    }
}
