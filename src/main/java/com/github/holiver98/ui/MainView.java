package com.github.holiver98.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;

@Theme("mytheme")
@Title("PizzaApp")
@SpringUI(path = "")
public class MainView extends UI {

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout content = new VerticalLayout();
        content.setStyleName("main");
        setContent(content);

        content.addComponent(new Footer());

        VerticalLayout body = new VerticalLayout();
        for(int i=0; i<15; i++){
            body.addComponent(new Label("Lorem ipsum"));

        }
        content.addComponent(body);

        Footer footer = new Footer();
        content.addComponent(footer);
    }

}
