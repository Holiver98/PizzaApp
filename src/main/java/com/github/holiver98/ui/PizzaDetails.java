package com.github.holiver98.ui;

import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.PizzaSize;
import com.github.holiver98.viewmodel.PizzaDetailsViewModel;
import com.vaadin.data.Binder;
import com.vaadin.data.ReadOnlyHasValue;
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

@SpringView(name = "pizza")
public class PizzaDetails extends VerticalLayout implements View {
    public interface PizzaDetailsViewListener{
        void newPizzaSelected(Long pizzaId);
    }

    @Autowired
    private PizzaDetailsViewModel viewModel;

    private Label pizzaNameL;
    private ComboBox<String> pizzaSizeCB;
    private VerticalLayout ingredientsVL;
    private Label pizzaPriceL;
    private Label pizzaRatingL;

    private Binder<Pizza> binder = new Binder<>();

    public PizzaDetails(){
        ReadOnlyHasValue<Pizza> nameLabelText = new ReadOnlyHasValue<>(
                pizza -> pizzaNameL.setValue(pizza.getName()));
        binder.forField(nameLabelText).bind(pizza -> pizza, null);

        pizzaRatingL = new Label("Rating: -");
        pizzaNameL = new Label("Name: -");
        ingredientsVL = new VerticalLayout();
        ingredientsVL.setCaption("Ingredients:");
        pizzaSizeCB = new ComboBox<>();
        pizzaSizeCB.setCaption("Size:");
        pizzaSizeCB.setEmptySelectionAllowed(false);
        pizzaSizeCB.setTextInputAllowed(false);
        pizzaSizeCB.setItems((Collection)EnumSet.allOf(PizzaSize.class));
        pizzaSizeCB.setValue("NORMAL");
        pizzaPriceL = new Label("Price: -");
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
        if (event.getParameters() != null) {
            String id = event.getParameters();
            viewModel.newPizzaSelected(Long.valueOf(id));
            binder.readBean(viewModel.getPizza()); //Read pizza values from viewModel
        }
    }

    /*
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if(event.getParameters() != null) {
            String id = event.getParameters();
            Pizza pizza = pizzaRepo.findById(Long.valueOf(id)).orElse(null);
            pizzaRatingL.setValue("Rating: "+ pizza.getRatingAverage() +" (?)");
            pizzaNameL.setValue("Name: " + pizza.getName() + " (via DB)");
            for(Ingredient ingredient : pizza.getIngredients()){
                ingredientsVL.addComponent(new Label(ingredient.getName()));
            }
            pizzaPriceL.setValue("Price: " + pizza.getPrice() + "$");
        }
    }*/
}
