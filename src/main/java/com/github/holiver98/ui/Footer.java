package com.github.holiver98.ui;

import com.vaadin.navigator.View;
import com.vaadin.server.Responsive;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

@SpringView
public class Footer extends CssLayout implements View {
    public Footer(){
        setStyleName("footer");
        Responsive.makeResponsive(this);
        setWidthFull();

        Label copyrightLabel = new Label("© 2020 - PizzaApp");
        addComponent(copyrightLabel);

        HorizontalLayout hl = new HorizontalLayout();
        hl.setStyleName("hl");
        Label emailLabel = new Label("email: support@pizzaapp.com");
        //emailLabel.setSizeUndefined();
        Label phoneLabel = new Label("phone: +77 77 777 7777");

        //phoneLabel.setSizeUndefined();
        hl.addComponent(emailLabel);
        hl.addComponent(phoneLabel);
        addComponent(hl);
    }
}
