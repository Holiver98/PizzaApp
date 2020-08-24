package com.github.holiver98.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Theme("mytheme")
@Title("PizzaApp")
@SpringUI
@SpringViewDisplay
public class MainView extends UI implements ViewDisplay {
    @Autowired
    private Header header;
    private Panel body = new Panel();
    private Footer footer;
    private VerticalLayout content = new VerticalLayout();

    Logger logger = LoggerFactory.getLogger(MainView.class);

    //ez később hívódik meg, mint a @PostConstruct
    @Override
    protected void init(VaadinRequest request){
        logger.info("mainview init() called");
        content = new VerticalLayout();
        content.setStyleName("main");
        setContent(content);

        content.addComponent(header);

        content.addComponent(body);

        footer = new Footer();
        content.addComponent(footer);
    }

    @Override
    public void showView(View view) {
        body.setContent(view.getViewComponent());
    }

    public Header getHeader(){
        return header;
    }
}
