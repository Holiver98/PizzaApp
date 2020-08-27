package com.github.holiver98.ui;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.IngredientType;
import com.github.holiver98.model.Pizza;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

@SpringView
public class CustomPizzaCartItem extends CssLayout implements View {
    private Button removeBtn;

    public CustomPizzaCartItem(Pizza pizza){
        HorizontalLayout leftLayout = new HorizontalLayout();
        setStyleName("customPizzaCartItem");

        VerticalLayout pizzaInfoVL = new VerticalLayout();
        pizzaInfoVL.addComponent(new Label(pizza.getName()));
        pizzaInfoVL.addComponent(new Label("Size: " + pizza.getSize().name()));
        pizzaInfoVL.addComponent(new Label("Base sauce: " + pizza.getIngredients().stream()
                .filter(ingredient -> ingredient.getType().equals(IngredientType.PIZZA_BASESAUCE))
                .findFirst().get().getName()));

        VerticalLayout toppingsVL = new VerticalLayout();
        toppingsVL.setStyleName("toppingsVL");
        toppingsVL.setCaption("Toppings:");
        for(Ingredient ingredient: pizza.getIngredients()){
            if(ingredient.getType().equals(IngredientType.PIZZA_TOPPING)){
                toppingsVL.addComponent(new Label(ingredient.getName()));
            }
        }
        pizzaInfoVL.addComponent(toppingsVL);
        pizzaInfoVL.addComponent(new Label(pizza.getPrice().toString() + "$"));
        leftLayout.addComponent(pizzaInfoVL);

        removeBtn = new Button("Remove");
        removeBtn.setStyleName("removeBtn");

        addComponent(leftLayout);
        addComponent(removeBtn);
    }

    public Button getRemoveBtn(){
        return removeBtn;
    }
}
