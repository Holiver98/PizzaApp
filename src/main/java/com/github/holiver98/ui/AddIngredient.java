package com.github.holiver98.ui;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.IngredientType;
import com.github.holiver98.model.Role;
import com.github.holiver98.model.User;
import com.github.holiver98.service.IPizzaService;
import com.github.holiver98.util.RequiresRole;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.EnumSet;

@RequiresRole(role = Role.CHEF)
@SpringView(name = "add_ingredient")
public class AddIngredient extends VerticalLayout implements View {
    @Autowired
    private IPizzaService pizzaService;
    Binder<Ingredient> binder;
    Ingredient item;

    private TextField nameTF;
    private ComboBox<IngredientType> typeComboBox;
    private TextField priceTF;

    public AddIngredient(){
        HorizontalLayout buttonContainer = new HorizontalLayout();
        Button addBtn = new Button("Add");
        addBtn.addClickListener(this::onAddBtnClicked);
        Button cancelBtn = new Button("Cancel");
        cancelBtn.addClickListener(this::onCancelBtnClicked);
        buttonContainer.addComponent(addBtn);
        buttonContainer.addComponent(cancelBtn);

        Label nameLabel = new Label("Name:");
        Label typelabel = new Label("Type:");
        Label priceLabel = new Label("Price:");

        nameTF = new TextField();
        typeComboBox = new ComboBox<>();
        typeComboBox.setEmptySelectionAllowed(false);
        typeComboBox.setTextInputAllowed(false);
        typeComboBox.setItems(EnumSet.allOf(IngredientType.class));
        priceTF = new TextField();

        item = new Ingredient();
        binder = new Binder<>();
        binder.setBean(item);
        bindFields(binder);

        //bind fields után, mert különben felülírja üres értékkel
        typeComboBox.setValue(IngredientType.PIZZA_TOPPING);

        addComponent(nameLabel);
        addComponent(nameTF);
        addComponent(typelabel);
        addComponent(typeComboBox);
        addComponent(priceLabel);
        addComponent(priceTF);
        addComponent(buttonContainer);
    }

    private void bindFields(Binder<Ingredient> binder) {
        binder.forField(nameTF)
                .withValidator(new StringLengthValidator("Incorrect length.", 1 , 40))
                .bind(Ingredient::getName, Ingredient::setName);

        binder.forField(typeComboBox)
                .bind(Ingredient::getType, Ingredient::setType);

        binder.forField(priceTF)
                .withConverter(new StringToBigDecimalConverter(
                        "Must enter a number (also use ',' instead of '.' as decimal point)."))
                .bind(Ingredient::getPrice, Ingredient::setPrice);
    }

    private void onAddBtnClicked(Button.ClickEvent clickEvent) {
        BinderValidationStatus<Ingredient> result = binder.validate();
        if(result.isOk() && !result.hasErrors()){
            try {
                binder.writeBean(item);
            } catch (ValidationException e) {
                e.printStackTrace();
            }

            onValidationSucceededAfterConfirmButtonPressed();
        }
    }

    private void onCancelBtnClicked(Button.ClickEvent clickEvent){
        getUI().getNavigator().navigateTo("ingredient_editor");
    }

    private void onValidationSucceededAfterConfirmButtonPressed() {
        MainView ui = (MainView)getUI();
        User loggedInUser = ui.getLoggedInUser()
                .orElseThrow(() -> new UnsupportedOperationException("Need to be logged in to create ingredient!"));
        pizzaService.saveIngredient(item, loggedInUser.getEmailAddress());

        ui.getNavigator().navigateTo("ingredient_editor");
        Notification.show("Successfully added ingredient.", Notification.Type.WARNING_MESSAGE);
    }
}
