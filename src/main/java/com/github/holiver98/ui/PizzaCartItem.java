package com.github.holiver98.ui;

import com.github.holiver98.model.Pizza;
import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

@SpringView
public class PizzaCartItem extends HorizontalLayout implements View {
    public PizzaCartItem(Pizza pizza){
        HorizontalLayout leftLayout = new HorizontalLayout();

        ThemeResource resource = new ThemeResource("images/testimg.png");
        Image pizzaImage = new Image("", resource);
        pizzaImage.setWidth("200");
        pizzaImage.setHeight("200");
        leftLayout.addComponent(pizzaImage);

        VerticalLayout pizzaInfoVL = new VerticalLayout();
        pizzaInfoVL.addComponent(new Label(pizza.getName()));
        pizzaInfoVL.addComponent(new Label(pizza.getPrice().toString() + "$"));
        leftLayout.addComponent(pizzaInfoVL);

        Button removeBtn = new Button("Remove");

        addComponent(leftLayout);
        addComponent(removeBtn);
    }
}
