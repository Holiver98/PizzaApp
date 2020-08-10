package com.github.holiver98.ui;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView
public class FooView extends VerticalLayout implements View {
    public FooView(){
        addComponent(new Label("Foo Boi"));
    }
}
