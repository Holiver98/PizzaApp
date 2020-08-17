package com.github.holiver98.ui;

import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

@SpringView
public class ItemCard extends AbsoluteLayout implements View {
    public ItemCard(String caption){
        setStyleName("itemcard");
        setWidth("250");
        setHeight("250");

        ThemeResource resource = new ThemeResource("images/testimg.png");
        Image image = new Image("", resource);
        image.setSizeFull();
        addComponent(image);

        Label captionLabel = new Label(caption);
        captionLabel.setWidthFull();
        captionLabel.setStyleName("captionlabel");
        addComponent(captionLabel, "z-index: 50; bottom: 0px; left: 0px;");
    }
}
