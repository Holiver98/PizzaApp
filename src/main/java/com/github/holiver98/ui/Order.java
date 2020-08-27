package com.github.holiver98.ui;

import com.github.holiver98.model.Pizza;
import com.github.holiver98.service.ICartService;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

@SpringView(name = "order")
public class Order extends VerticalLayout implements View {
    @Autowired
    private ICartService cartService;
    private VerticalLayout itemListVL;
    private Label totalPriceLabel;

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

        Button orderBtn = new Button("Order");

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
}
