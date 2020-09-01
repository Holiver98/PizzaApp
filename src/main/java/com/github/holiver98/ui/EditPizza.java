package com.github.holiver98.ui;

import com.github.holiver98.model.*;
import com.github.holiver98.service.IPizzaService;
import com.github.holiver98.util.RequiresRole;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.ValidationException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

//TODO: a pizza árát számított értékként kezeltem egész eddig, pedig nem kéne annak lennie
@RequiresRole(role = Role.CHEF)
@SpringView(name = "edit_pizza")
public class EditPizza extends VerticalLayout implements View {
    @Autowired
    protected IPizzaService pizzaService;

    private TextField nameTF;
    protected ComboBox<Ingredient> basesauceCB;
    private TwinColSelect<String> ingredientsTCS;
    private ComboBox<PizzaSize> sizeCB;
    private TextField priceTF;
    private CheckBox isCustomChB;

    private Binder<Pizza> binder;
    protected Pizza item;

    protected Button confirmBtn;

    public EditPizza(){
        Label nameLabel = new Label("Name:");
        Label basesauceLabel = new Label("Base sauce:");
        Label ingredientsLabel = new Label("Ingredients:");
        Label sizeLabel = new Label("Size:");
        Label priceLabel = new Label("Price:");
        Label isCustomLabel = new Label("Custom:");

        nameTF = new TextField();
        basesauceCB = new ComboBox<>();
        basesauceCB.setEmptySelectionAllowed(false);
        basesauceCB.setTextInputAllowed(false);
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

        confirmBtn = new Button("Edit");
        confirmBtn.addClickListener(clickEvent -> onConfirmButtonPressed());
        Button cancelBtn = new Button("Cancel");
        cancelBtn.addClickListener(clickEvent -> getUI().getNavigator().navigateTo("pizza_editor"));
        HorizontalLayout buttonContainer = new HorizontalLayout();
        buttonContainer.addComponent(confirmBtn);
        buttonContainer.addComponent(cancelBtn);

        binder = new Binder<>();
        createBindings();

        addComponent(nameLabel);
        addComponent(nameTF);
        addComponent(basesauceLabel);
        addComponent(basesauceCB);
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
        loadItemsIntoIngredientsTCS();

        if(event.getParameters() != null) {
            String stringId = event.getParameters();
            Long id = Long.parseLong(stringId);
            Optional<Pizza> optionalPizza = pizzaService.getPizzaById(id);
            item = optionalPizza.orElseThrow(() -> new RuntimeException("pizza not found with id: " + id));

            updateDataOnUi(item);
        }

        setBaseSauceComboBoxItems();
    }

    private void setBaseSauceComboBoxItems(){
        List<Ingredient> baseSauces = pizzaService.getIngredients().stream()
                .filter(ingredient -> ingredient.getType().equals(IngredientType.PIZZA_BASESAUCE))
                .collect(Collectors.toList());

        if(baseSauces.isEmpty()){
            return;
        }

        basesauceCB.setItems(baseSauces);
        basesauceCB.setItemCaptionGenerator(Ingredient::getName);
    }

    private Ingredient getBasesauce(Pizza pizza) {
        return pizza.getIngredients().stream()
                .filter(ingredient -> ingredient.getType().equals(IngredientType.PIZZA_BASESAUCE))
                .findFirst()
                .orElse(null);
    }

    private void setBasesauce(Pizza pizza, Ingredient basesauce){
        pizza.getIngredients().removeIf(ingredient -> ingredient.getType().equals(IngredientType.PIZZA_BASESAUCE));
        pizza.getIngredients().add(basesauce);
    }

    protected void loadItemsIntoIngredientsTCS() {
        List<Ingredient> ingredients = pizzaService.getIngredients();
        List<String> ingredientNames = ingredients.stream()
                .filter(ingredient -> ingredient.getType().equals(IngredientType.PIZZA_TOPPING))
                .map(ingredient -> ingredient.getName())
                .collect(Collectors.toList());
        ingredientsTCS.setItems(ingredientNames);
    }

    private void updateDataOnUi(Pizza pizza) {
        binder.readBean(item);
        pizza.getIngredients().stream()
                .filter(ingredient -> ingredient.getType().equals(IngredientType.PIZZA_TOPPING))
                .forEach(ingredient -> ingredientsTCS.select(ingredient.getName()));
    }

    private void createBindings() {
        binder = new Binder<>();
        binder.setBean(item);

        binder.forField(nameTF)
                .bind(Pizza::getName, Pizza::setName);

        binder.forField(basesauceCB)
                .bind(pizza -> getBasesauce(pizza), (pizza, ingredient) -> setBasesauce(pizza, ingredient));

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

    private void onConfirmButtonPressed() {
        BinderValidationStatus<Pizza> result = binder.validate();
        if(result.isOk() && !result.hasErrors()){
            try {
                binder.writeBean(item);
                setToppingsOnPizza(item, ingredientsTCS.getSelectedItems());
            } catch (ValidationException e) {
                e.printStackTrace();
            }

            onValidationSucceededAfterConfirmButtonPressed();
        }
    }

    protected void onValidationSucceededAfterConfirmButtonPressed() {
        MainView ui = (MainView)getUI();
        User loggedInUser = ui.getLoggedInUser()
                .orElseThrow(() -> new UnsupportedOperationException("Need to be logged in to edit pizza!"));
        pizzaService.updatePizza(item, loggedInUser.getEmailAddress());

        ui.getNavigator().navigateTo("pizza_editor");
        Notification.show("Successfully edited pizza.", Notification.Type.WARNING_MESSAGE);
    }
}
