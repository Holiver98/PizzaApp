package com.github.holiver98.ui;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.PizzaSize;
import com.github.holiver98.service.CartIsFullException;
import com.github.holiver98.service.ICartService;
import com.github.holiver98.service.IPizzaService;
import com.github.holiver98.service.IRatingService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.teemu.ratingstars.RatingStars;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Optional;

@SpringView(name = "pizza")
public class PizzaDetails extends VerticalLayout implements View {
    @Autowired
    IPizzaService pizzaService;
    @Autowired
    IRatingService ratingService;
    @Autowired
    ICartService cartService;

    private Pizza item;

    private Label pizzaNameL;
    private ComboBox<String> pizzaSizeCB;
    private VerticalLayout ingredientsVL;
    private Label pizzaPriceL;
    private Label pizzaRatingL;
    private RatingStars ratingStars;

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
        addToCartBtn.addClickListener(clickEvent -> {
            try {
                cartService.addPizzaToCart(item);
            } catch (CartIsFullException e) {
                Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            }
        });

        addComponent(pizzaRatingL);
        ratingStars = new RatingStars();
        ratingStars.setAnimated(false);
        ratingStars.setReadOnly(true);
        addComponent(ratingStars);
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
            item = pizza.get();
            int numberOfRatingsOnPizza = ratingService.getRatingsOfPizza(Long.parseLong(id)).size();
            pizza.ifPresent(p -> updateUiWithModel(p, numberOfRatingsOnPizza));
        }
    }

    private void updateUiWithModel(Pizza pizza, int numberOfRatingsOnPizza){
        pizzaRatingL.setValue(ratingLabelBaseText + pizza.getRatingAverage() + " (" + numberOfRatingsOnPizza + ")");
        ratingStars.setValue(pizza.getRatingAverage().doubleValue());
        pizzaNameL.setValue(nameLabelBaseText + pizza.getName());
        for(Ingredient ingredient : pizza.getIngredients()){
            ingredientsVL.addComponent(new Label(ingredient.getName()));
        }
        pizzaPriceL.setValue(priceLabelBaseText + pizza.getPrice() + "$");
    }
}
