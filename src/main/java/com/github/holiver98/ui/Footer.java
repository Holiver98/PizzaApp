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

        HorizontalLayout supportInfoContainer = new HorizontalLayout();
        supportInfoContainer.setStyleName("supportInfoContainer");
        Label emailLabel = new Label("email: support@pizzaapp.com");
        Label phoneLabel = new Label("phone: +77 77 777 7777");
        supportInfoContainer.addComponent(emailLabel);
        supportInfoContainer.addComponent(phoneLabel);
        addComponent(supportInfoContainer);
    }
}
