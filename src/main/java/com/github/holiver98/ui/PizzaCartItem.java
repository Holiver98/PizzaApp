package com.github.holiver98.ui;

import com.github.holiver98.model.Pizza;
import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

@SpringView
public class PizzaCartItem extends CssLayout implements View {
    private Button removeBtn;

    public PizzaCartItem(Pizza pizza){
        HorizontalLayout leftLayout = new HorizontalLayout();
        leftLayout.setStyleName("leftLayout");
        setStyleName("pizzaCartItem");

        ThemeResource resource = new ThemeResource("images/testimg.png");
        Image pizzaImage = new Image(null, resource);
        pizzaImage.setStyleName("pizzaImage");
        pizzaImage.setWidth("200");
        pizzaImage.setHeight("200");
        leftLayout.addComponent(pizzaImage);

        VerticalLayout pizzaInfoVL = new VerticalLayout();
        pizzaInfoVL.addComponent(new Label(pizza.getName()));
        pizzaInfoVL.addComponent(new Label("Size: " + pizza.getSize().name()));
        pizzaInfoVL.addComponent(new Label(pizza.getPrice().toString() + "$"));
        leftLayout.addComponent(pizzaInfoVL);

        removeBtn = new Button("Remove");
        removeBtn.setStyleName("removeBtn");

        addComponent(leftLayout);
        addComponent(removeBtn);
    }

    public Button getRemoveBtn(){
        return removeBtn;
    }
}
