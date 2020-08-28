package com.github.holiver98.ui;

import com.github.holiver98.model.*;
import com.github.holiver98.service.IPizzaService;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.ValidationException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@SpringView(name = "edit_pizzas_details")
public class EditPizzasDetails extends VerticalLayout implements View {
    @Autowired
    private IPizzaService pizzaService;

    private Label nameLabel;
    private Label ingredientsLabel;
    private Label sizeLabel;
    //TODO: a pizza árát számított értékként kezeltem egész eddig, pedig nem kéne annak lennie
    private Label priceLabel;
    private Label isCustomLabel;

    private TextField nameTF;
    private TwinColSelect<String> ingredientsTCS;
    private ComboBox<PizzaSize> sizeCB;
    private TextField priceTF;
    private CheckBox isCustomChB;

    private Binder<Pizza> binder;
    private Pizza item;

    private Button editBtn;

    public EditPizzasDetails(){
        nameLabel = new Label("Name:");
        ingredientsLabel = new Label("Ingredients:");
        sizeLabel = new Label("Size:");
        priceLabel = new Label("Price:");
        isCustomLabel = new Label("Custom:");

        nameTF = new TextField();
        ingredientsTCS = new TwinColSelect<>();
        ingredientsTCS.setLeftColumnCaption("All ingredients");
        ingredientsTCS.setRightColumnCaption("Selected ingredients");
        ingredientsTCS.setRows(7);
        sizeCB = new ComboBox<>();
        sizeCB.setEmptySelectionAllowed(false);
        sizeCB.setTextInputAllowed(false);
        sizeCB.setItems(EnumSet.allOf(PizzaSize.class));
        priceTF = new TextField();
        isCustomChB = new CheckBox();

        editBtn = new Button("Edit");
        editBtn.addClickListener(clickEvent -> onEditButtonPressed());
        Button cancelBtn = new Button("Cancel");
        cancelBtn.addClickListener(clickEvent -> getUI().getNavigator().navigateTo("edit_pizzas"));
        HorizontalLayout buttonContainer = new HorizontalLayout();
        buttonContainer.addComponent(editBtn);
        buttonContainer.addComponent(cancelBtn);

        binder = new Binder<>();
        createBindings();

        addComponent(nameLabel);
        addComponent(nameTF);
        addComponent(ingredientsLabel);
        addComponent(ingredientsTCS);
        addComponent(sizeLabel);
        addComponent(sizeCB);
        addComponent(priceLabel);
        addComponent(priceTF);
        addComponent(isCustomLabel);
        addComponent(isCustomChB);
        addComponent(buttonContainer);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        List<Ingredient> ingredients = pizzaService.getIngredients();
        List<String> ingredientNames = ingredients.stream()
                .filter(ingredient -> ingredient.getType().equals(IngredientType.PIZZA_TOPPING))
                .map(ingredient -> ingredient.getName())
                .collect(Collectors.toList());
        ingredientsTCS.setItems(ingredientNames);

        if(event.getParameters() != null) {
            String stringId = event.getParameters();
            Long id = Long.parseLong(stringId);
            Optional<Pizza> optionalPizza = pizzaService.getPizzaById(id);
            item = optionalPizza.orElseThrow(() -> new RuntimeException("pizza not found with id: " + id));

            updateDataOnUi(item);
        }
    }

    private void updateDataOnUi(Pizza pizza) {
        binder.readBean(item);
        pizza.getIngredients().forEach(ingredient -> ingredientsTCS.select(ingredient.getName()));
    }

    private void createBindings() {
        binder = new Binder<>();
        binder.setBean(item);

        binder.forField(nameTF)
                .bind(Pizza::getName, Pizza::setName);

        binder.forField(priceTF)
                .bind(pizza -> pizza.getPrice().toString(), (pizza, priceString) -> pizza.setPrice(new BigDecimal(priceString)));

        binder.forField(isCustomChB)
                .bind(Pizza::isCustom, Pizza::setCustom);

        binder.forField(sizeCB)
                .bind(Pizza::getSize, Pizza::setSize);

        //az ingredientsTCS binddal nem nagyon működdött.. azt inkább kézzel töltöm fel / olvasom ki
    }

    private void setToppingsOnPizza(Pizza pizza, Set<String> toppingString) {
        List<Ingredient> allIngredients = pizzaService.getIngredients();
        List<Ingredient> toppingsThatAreInTheSet = toppingString.stream()
                .map(toppingStr -> getIngredientFromListWithName(allIngredients, toppingStr))
                .collect(Collectors.toList());

        pizza.getIngredients().removeIf(ingredient -> ingredient.getType().equals(IngredientType.PIZZA_TOPPING));
        pizza.getIngredients().addAll(toppingsThatAreInTheSet);
    }

    private Ingredient getIngredientFromListWithName(List<Ingredient> ingredientList, String name) {
        return ingredientList.stream()
                .filter(ingredient -> ingredient.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NullPointerException("No ingredient in the list with name: " + name));
    }

    private void onEditButtonPressed() {
        BinderValidationStatus<Pizza> result = binder.validate();
        if(result.isOk() && !result.hasErrors()){
            try {
                binder.writeBean(item);
                setToppingsOnPizza(item, ingredientsTCS.getSelectedItems());
            } catch (ValidationException e) {
                e.printStackTrace();
            }

            MainView ui = (MainView)getUI();
            User loggedInUser = ui.getLoggedInUser()
                    .orElseThrow(() -> new UnsupportedOperationException("Need to be logged in to edit pizza!"));
            pizzaService.updatePizza(item, loggedInUser.getEmailAddress());

            ui.getNavigator().navigateTo("edit_pizzas");
            Notification.show("Successfully edited pizza.", Notification.Type.WARNING_MESSAGE);
        }
    }
}
