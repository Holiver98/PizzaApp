package com.github.holiver98.ui;

import com.vaadin.navigator.View;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = "")
public class Home extends VerticalLayout implements View {
    public Home(){
        Label title = new Label("PizzaApp");
        title.setStyleName("title");
        addComponent(title);
        setComponentAlignment(title, Alignment.MIDDLE_CENTER);

        Label separator =  new Label("<hr />", ContentMode.HTML);
        addComponent(separator);
        separator.setWidth(80, Sizeable.Unit.PERCENTAGE);
        setComponentAlignment(separator, Alignment.MIDDLE_CENTER);

        CssLayout pizzaList = new CssLayout();
        pizzaList.setStyleName("pizzalist");
        for(int i=0; i<15; i++){
            pizzaList.addComponent(new PizzaCard());
        }
        addComponent(pizzaList);
    }
}
