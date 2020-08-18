package com.github.holiver98.ui;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.PizzaSize;
import com.github.holiver98.service.IPizzaService;
import com.github.holiver98.service.IRatingService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Optional;

@SpringView(name = "pizza")
public class PizzaDetails extends VerticalLayout implements View {
    @Autowired
    IPizzaService pizzaService;
    @Autowired
    IRatingService ratingService;

    private Label pizzaNameL;
    private ComboBox<String> pizzaSizeCB;
    private VerticalLayout ingredientsVL;
    private Label pizzaPriceL;
    private Label pizzaRatingL;

    private static final String ratingLabelBaseText = "Rating: ";
    private static final String nameLabelBaseText = "Name: ";
    private static final String priceLabelBaseText = "Price: ";

    public PizzaDetails(){
        pizzaRatingL = new Label(ratingLabelBaseText);
        pizzaNameL = new Label(nameLabelBaseText);
        ingredientsVL = new VerticalLayout();
        ingredientsVL.setCaption("Ingredients:");
        pizzaSizeCB = new ComboBox<>();
        pizzaSizeCB.setCaption("Size:");
        pizzaSizeCB.setEmptySelectionAllowed(false);
        pizzaSizeCB.setTextInputAllowed(false);
        pizzaSizeCB.setItems((Collection)EnumSet.allOf(PizzaSize.class));
        pizzaSizeCB.setValue("NORMAL");
        pizzaPriceL = new Label(priceLabelBaseText);
        Button addToCartBtn = new Button("Add to cart");

        addComponent(pizzaRatingL);
        addComponent(pizzaNameL);
        addComponent(ingredientsVL);
        addComponent(pizzaSizeCB);
        addComponent(pizzaPriceL);
        addComponent(addToCartBtn);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if(event.getParameters() != null) {
            String id = event.getParameters();
            Optional<Pizza> pizza = pizzaService.getPizzaById(Long.parseLong(id));
            int numberOfRatingsOnPizza = ratingService.getRatingsOfPizza(Long.parseLong(id)).size();
            pizza.ifPresent(p -> updateUiWithModel(p, numberOfRatingsOnPizza));
        }
    }

    private void updateUiWithModel(Pizza pizza, int numberOfRatingsOnPizza){
        pizzaRatingL.setValue(ratingLabelBaseText + pizza.getRatingAverage() + " (" + numberOfRatingsOnPizza + ")");
        pizzaNameL.setValue(nameLabelBaseText + pizza.getName());
        for(Ingredient ingredient : pizza.getIngredients()){
            ingredientsVL.addComponent(new Label(ingredient.getName()));
        }
        pizzaPriceL.setValue(priceLabelBaseText + pizza.getPrice() + "$");
    }
}
