package com.github.holiver98.ui;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.IngredientType;
import com.github.holiver98.model.Pizza;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

@SpringView
public class CustomPizzaCartItem extends HorizontalLayout implements View {
    private Button removeBtn;

    public CustomPizzaCartItem(Pizza pizza){
        HorizontalLayout leftLayout = new HorizontalLayout();

        VerticalLayout pizzaInfoVL = new VerticalLayout();
        pizzaInfoVL.addComponent(new Label(pizza.getName()));
        pizzaInfoVL.addComponent(new Label(pizza.getPrice().toString() + "$"));
        pizzaInfoVL.addComponent(new Label("Base sauce: " + pizza.getIngredients().stream()
                .filter(ingredient -> ingredient.getType().equals(IngredientType.PIZZA_BASESAUCE))
                .findFirst().get().getName()));
        VerticalLayout toppingsVL = new VerticalLayout();
        toppingsVL.setCaption("Toppings:");
        for(Ingredient ingredient: pizza.getIngredients()){
            if(ingredient.getType().equals(IngredientType.PIZZA_TOPPING)){
                toppingsVL.addComponent(new Label(ingredient.getName()));
            }
        }
        pizzaInfoVL.addComponent(toppingsVL);
        leftLayout.addComponent(pizzaInfoVL);

        removeBtn = new Button("Remove");

        addComponent(leftLayout);
        addComponent(removeBtn);
    }

    public Button getRemoveBtn(){
        return removeBtn;
    }
}
