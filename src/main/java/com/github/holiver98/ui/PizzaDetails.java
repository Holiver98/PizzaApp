package com.github.holiver98.ui;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.PizzaSize;
import com.github.holiver98.model.User;
import com.github.holiver98.service.*;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.teemu.ratingstars.RatingStars;

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
    private ComboBox<PizzaSize> pizzaSizeCB;
    private VerticalLayout ingredientsVL;
    private Label pizzaPriceL;
    private Label pizzaRatingL;

    private RatingStars ratingStars;
    private Button ratingButton;
    private Button confirmRating;

    private static final String ratingLabelBaseText = "Rating: ";
    private static final String nameLabelBaseText = "Name: ";
    private static final String priceLabelBaseText = "Price: ";

    public PizzaDetails(){
        pizzaRatingL = new Label(ratingLabelBaseText);
        pizzaNameL = new Label(nameLabelBaseText);
        pizzaPriceL = new Label(priceLabelBaseText);

        ingredientsVL = new VerticalLayout();
        ingredientsVL.setCaption("Ingredients:");

        pizzaSizeCB = new ComboBox<>();
        pizzaSizeCB.setCaption("Size:");
        pizzaSizeCB.setEmptySelectionAllowed(false);
        pizzaSizeCB.setTextInputAllowed(false);
        pizzaSizeCB.setItems(EnumSet.allOf(PizzaSize.class));
        pizzaSizeCB.setValue(PizzaSize.NORMAL);

        Button addToCartBtn = new Button("Add to cart");
        addToCartBtn.addClickListener(this::onCartButtonPressed);

        ratingButton = new Button("Rate");
        ratingButton.addClickListener(this::onRatingButtonPressed);
        confirmRating = new Button("Confirm");
        confirmRating.addClickListener(this::OnConfirmRatingButtonPressed);
        confirmRating.setVisible(false);

        ratingStars = new RatingStars();
        ratingStars.setAnimated(false);
        ratingStars.setReadOnly(true);

        HorizontalLayout ratingContainer = new HorizontalLayout();
        ratingContainer.addComponent(ratingStars);
        ratingContainer.addComponent(ratingButton);
        ratingContainer.addComponent(confirmRating);

        addComponent(pizzaRatingL);
        addComponent(ratingContainer);
        addComponent(pizzaNameL);
        addComponent(ingredientsVL);
        addComponent(pizzaSizeCB);
        addComponent(pizzaPriceL);
        addComponent(addToCartBtn);
    }

    private void OnConfirmRatingButtonPressed(Button.ClickEvent clickEvent){
        int rating = ratingStars.getValue().intValue();

        User loggedInUser = new User();
        try {
            loggedInUser = ((MainView) getUI()).getLoggedInUser()
                    .orElseThrow(() -> new UnsupportedOperationException(""));
        }catch(UnsupportedOperationException e){
            Notification.show("You have to be logged in to rate!", Notification.Type.ERROR_MESSAGE);
            exitRatingMode();
            ratingStars.setValue(item.getRatingAverage().doubleValue());
            return;
        }

        try {
            ratingService.ratePizza(item.getId(), rating, loggedInUser.getEmailAddress());
        } catch (NotFoundException e) {
            Notification.show("Internal server error: No pizza was found in the database with id: " + item.getId(), Notification.Type.ERROR_MESSAGE);
            exitRatingMode();
            ratingStars.setValue(item.getRatingAverage().doubleValue());
            return;
        } catch (UnsupportedOperationException e){
            Notification.show("You already rated this pizza!", Notification.Type.WARNING_MESSAGE);
            exitRatingMode();
            ratingStars.setValue(item.getRatingAverage().doubleValue());
            return;
        }

        updateRatingOnUiAndPizza();
        exitRatingMode();
    }

    private void exitRatingMode() {
        ratingStars.setReadOnly(true);
        ratingButton.setVisible(true);
        confirmRating.setVisible(false);
    }

    private void enterRatingMode() {
        ratingStars.setReadOnly(false);
        ratingButton.setVisible(false);
        confirmRating.setVisible(true);
    }

    private void updateRatingOnUiAndPizza() {
        Optional<Pizza> pizza = pizzaService.getPizzaById(item.getId());
        if(!pizza.isPresent()){
            Notification.show("Internal server error: No pizza was found in the database with id: " + item.getId(), Notification.Type.ERROR_MESSAGE);
            return;
        }else{
            item = pizza.get();
        }

        ratingStars.setValue(item.getRatingAverage().doubleValue());
        int numberOfRatingsOnPizza = ratingService.getRatingsOfPizza(item.getId()).size();
        pizzaRatingL.setValue(ratingLabelBaseText + item.getRatingAverage() + " (" + numberOfRatingsOnPizza + ")");
    }

    private void onRatingButtonPressed(Button.ClickEvent clickEvent) {
        Optional<User> loggedInUser = ((MainView)getUI()).getLoggedInUser();
        if(!loggedInUser.isPresent()){
            Notification.show("Have to be logged in.", Notification.Type.WARNING_MESSAGE);
            return;
        }

        enterRatingMode();
    }

    private void onCartButtonPressed(Button.ClickEvent clickEvent) {
        try {
            item.setSize(pizzaSizeCB.getValue());
            cartService.addPizzaToCart(item);
        } catch (CartIsFullException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
        }
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
