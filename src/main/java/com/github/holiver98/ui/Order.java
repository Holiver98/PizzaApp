package com.github.holiver98.ui;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = "order")
public class Order extends VerticalLayout implements View {
    public Order(){
        Panel contentPanel = new Panel();
        VerticalLayout orderInfoVL = new VerticalLayout();
        contentPanel.setContent(orderInfoVL);

        VerticalLayout itemListVL = new VerticalLayout();
        itemListVL.setCaption("Pizzas:");
        Label totalPriceLabel = new Label("Total price: 0.00$");
        orderInfoVL.addComponent(itemListVL);
        orderInfoVL.addComponent(totalPriceLabel);

        Button orderBtn = new Button("Order");

        addComponent(contentPanel);
        addComponent(orderBtn);
    }
}
