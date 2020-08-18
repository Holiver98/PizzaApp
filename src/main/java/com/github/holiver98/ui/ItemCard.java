package com.github.holiver98.ui;

import com.vaadin.navigator.View;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

@SpringView
public class ItemCard extends AbsoluteLayout implements View {
    private Image image;
    private Label captionLabel;

    public ItemCard(){
        setStyleName("itemcard");
        setWidth("250");
        setHeight("250");

        ThemeResource resource = new ThemeResource("images/testimg.png");
        image = new Image("", resource);
        image.setSizeFull();
        addComponent(image);

        captionLabel = new Label();
        captionLabel.setWidthFull();
        captionLabel.setStyleName("captionlabel");
        addComponent(captionLabel, "z-index: 50; bottom: 0px; left: 0px;");
    }

    public ItemCard(String caption){
        this();
        setCaption(caption);
    }

    public void setImage(Resource resource){
        this.image.setSource(resource);
    }

    public void setCaption(String caption){
        captionLabel.setValue(caption);
    }
}
