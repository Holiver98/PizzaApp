package com.github.holiver98.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

@SpringView
public class Header extends Panel implements View {
    public Header(){
        HorizontalLayout hl = new HorizontalLayout();
        setHeight("200");
        setContent(hl);
        hl.addComponent(new Label("logo"));
        hl.addComponent(new Label("Home"));
        hl.addComponent(new Button("hello"));
    }
}
