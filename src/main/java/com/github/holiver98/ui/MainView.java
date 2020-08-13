package com.github.holiver98.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;

@Theme("mytheme")
@Title("PizzaApp")
@SpringUI
@SpringViewDisplay
public class MainView extends UI implements ViewDisplay {

    private Panel body = new Panel();

    @Override
    protected void init(VaadinRequest request){
        VerticalLayout content = new VerticalLayout();
        content.setStyleName("main");
        setContent(content);

        Header header = new Header();
        content.addComponent(header);

        content.addComponent(body);

        Footer footer = new Footer();
        content.addComponent(footer);
    }

    @Override
    public void showView(View view) {
        body.setContent(view.getViewComponent());
    }
}
