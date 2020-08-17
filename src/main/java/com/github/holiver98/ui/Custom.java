package com.github.holiver98.ui;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

@SpringView(name = "custom")
public class Custom extends HorizontalLayout implements View {
    public Custom(){
        VerticalLayout leftLayout = new VerticalLayout();
        Panel rightPanel = new Panel();
        rightPanel.setHeight("400");
        VerticalLayout rightList = new VerticalLayout();
        rightPanel.setContent(rightList);

        addComponent(leftLayout);
        addComponent(rightPanel);

        ComboBox<String> basesauceCB = new ComboBox<>("Base sauce");
        basesauceCB.setEmptySelectionAllowed(false);
        basesauceCB.setTextInputAllowed(false);
        basesauceCB.setItems("Tomato", "Sour cream");

        VerticalLayout selectedToppingsVL = new VerticalLayout();
        selectedToppingsVL.setCaption("Toppings");
        Label totalPriceLabel = new Label("Total price: 0.00$");
        Button addToCartBtn = new Button("Add to cart");

        leftLayout.addComponent(basesauceCB);
        leftLayout.addComponent(selectedToppingsVL);
        leftLayout.addComponent(totalPriceLabel);
        leftLayout.addComponent(addToCartBtn);

        for(int i = 0; i < 8; i++){
            ItemCard item = new ItemCard(String.valueOf(i));
            item.setHeight("200");
            item.setWidth("200");
            item.addLayoutClickListener(layoutClickEvent -> Notification.show(layoutClickEvent.getComponent().getClass().getName()));
            rightList.addComponent(item);
        }
    }
}
