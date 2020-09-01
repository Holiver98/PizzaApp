package com.github.holiver98.ui;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.Role;
import com.github.holiver98.model.User;
import com.github.holiver98.service.IPizzaService;
import com.github.holiver98.util.RequiresRole;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@RequiresRole(role = Role.CHEF)
@SpringView(name = "edit_ingredient")
public class EditIngredient extends VerticalLayout implements View {
    @Autowired
    private IPizzaService pizzaService;
    Binder<Ingredient> binder;
    Ingredient item;

    private Label nameLabel;
    private TextField priceTF;

    public EditIngredient(){
        HorizontalLayout buttonContainer = new HorizontalLayout();
        Button editBtn = new Button("Edit");
        editBtn.addClickListener(this::onEditBtnClicked);
        Button cancelBtn = new Button("Cancel");
        cancelBtn.addClickListener(this::onCancelBtnClicked);
        buttonContainer.addComponent(editBtn);
        buttonContainer.addComponent(cancelBtn);

        Label nameCaptionLabel = new Label("Name:");
        nameLabel = new Label("-");
        Label priceLabel = new Label("Price:");

        priceTF = new TextField();

        item = new Ingredient();
        binder = new Binder<>();
        binder.setBean(item);
        bindFields(binder);

        addComponent(nameCaptionLabel);
        addComponent(nameLabel);
        addComponent(priceLabel);
        addComponent(priceTF);
        addComponent(buttonContainer);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if(event.getParameters() != null) {
            String ingredientName = event.getParameters();
            Optional<Ingredient> optionalIngredient = pizzaService.getPizzaIngredientByName(ingredientName);
            item = optionalIngredient.orElseThrow(() -> new RuntimeException("ingredient not found with name: " + ingredientName));
            updateDataOnUi();
        }
    }

    private void updateDataOnUi() {
        nameLabel.setValue(item.getName());
        binder.readBean(item);
    }

    private void bindFields(Binder<Ingredient> binder) {
        binder.forField(priceTF)
                .withConverter(new StringToBigDecimalConverter(
                        "Must enter a number (also use ',' instead of '.' as decimal point)."))
                .bind(Ingredient::getPrice, Ingredient::setPrice);
    }

    private void onEditBtnClicked(Button.ClickEvent clickEvent) {
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
                .orElseThrow(() -> new UnsupportedOperationException("Need to be logged in to edit ingredient!"));
        pizzaService.updateIngredient(item, loggedInUser.getEmailAddress());

        ui.getNavigator().navigateTo("ingredient_editor");
        Notification.show("Successfully edited ingredient.", Notification.Type.WARNING_MESSAGE);
    }
}
