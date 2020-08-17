package com.github.holiver98.ui;

import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

@SpringView
public class PizzaCartItem extends HorizontalLayout implements View {
    public PizzaCartItem(){
        HorizontalLayout leftLayout = new HorizontalLayout();

        ThemeResource resource = new ThemeResource("images/testimg.png");
        Image pizzaImage = new Image("", resource);
        pizzaImage.setWidth("200");
        pizzaImage.setHeight("200");
        leftLayout.addComponent(pizzaImage);

        VerticalLayout pizzaInfoVL = new VerticalLayout();
        pizzaInfoVL.addComponent(new Label("Pepperoni pizza"));
        pizzaInfoVL.addComponent(new Label("18.04$"));
        leftLayout.addComponent(pizzaInfoVL);

        Button removeBtn = new Button("Remove");

        addComponent(leftLayout);
        addComponent(removeBtn);
    }
}
