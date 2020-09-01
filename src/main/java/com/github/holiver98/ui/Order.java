package com.github.holiver98.ui;

import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.User;
import com.github.holiver98.service.CartIsEmptyException;
import com.github.holiver98.service.ICartService;
import com.github.holiver98.util.RequiresAuthentication;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiresAuthentication
@SpringView(name = "order")
public class Order extends VerticalLayout implements View {
    @Autowired
    private ICartService cartService;
    private VerticalLayout itemListVL;
    private Label totalPriceLabel;
    private Button orderBtn;

    private static final String totalPriceLabelText = "Total price: ";

    public Order(){
        Panel contentPanel = new Panel();
        VerticalLayout orderInfoVL = new VerticalLayout();
        contentPanel.setContent(orderInfoVL);

        itemListVL = new VerticalLayout();
        itemListVL.setCaption("Pizzas:");
        totalPriceLabel = new Label(totalPriceLabelText + "0.00$");
        orderInfoVL.addComponent(itemListVL);
        orderInfoVL.addComponent(totalPriceLabel);

        orderBtn = new Button("Order");
        orderBtn.addClickListener(clickEvent -> onOrderButtonPressed());

        addComponent(contentPanel);
        addComponent(orderBtn);
    }

    @PostConstruct
    private void afterInit(){
        List<Pizza> items = cartService.getCartContent();//TODO: update all clients memory after successfully editing pizza
        addPizzasToUi(items);
        calculateTotalPrice(items);
    }

    private void addPizzasToUi(List<Pizza> items) {
        itemListVL.removeAllComponents();
        for(Pizza pizza : items){
            itemListVL.addComponent(new Label(pizza.getName() + " - " + pizza.getPrice() + "$"));
        }
    }

    private void calculateTotalPrice(List<Pizza> items) {
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for(Pizza pizza : items){
            totalPrice = totalPrice.add(pizza.getPrice());
        }
        totalPriceLabel.setValue(totalPriceLabelText + totalPrice.toString() + "$");
    }

    private void onOrderButtonPressed(){
        Optional<User> loggedInUser = ((MainView)getUI()).getLoggedInUser();
        if(loggedInUser.isPresent()){
            try {
                cartService.placeOrder(loggedInUser.get().getEmailAddress());
            } catch (CartIsEmptyException e) {
                Notification.show("Cart is empty.", Notification.Type.WARNING_MESSAGE);
                return;
            } catch (MessagingException e) {
                Notification.show("Internal server error.", Notification.Type.ERROR_MESSAGE);
                e.printStackTrace();
                return;
            }
        }else{
            getUI().getNavigator().navigateTo("login");
            Notification.show("You need to be logged in.", Notification.Type.WARNING_MESSAGE);
            return;
        }

        getUI().getNavigator().navigateTo("");
        cartService.clearContent();
        Notification.show("Order placed successfully.", Notification.Type.WARNING_MESSAGE);
    }
}
