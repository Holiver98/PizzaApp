package com.github.holiver98.ui;

import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

@SpringView
public class PizzaCard extends VerticalLayout implements View {
    public PizzaCard(){
        setStyleName("pizzacard");
        setWidthUndefined();

        ThemeResource resource = new ThemeResource("images/testimg.png");
        Image image = new Image("", resource);
        image.setHeight("250");
        image.setWidth("250");
        addComponent(image);

        Label pizzaLabel = new Label("Pizza");
        addComponent(pizzaLabel);
        setComponentAlignment(pizzaLabel, Alignment.MIDDLE_CENTER);

    }
}
