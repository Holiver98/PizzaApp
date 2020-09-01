package com.github.holiver98.ui;

import com.github.holiver98.model.*;
import com.github.holiver98.util.RequiresRole;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Notification;

import java.util.List;
import java.util.stream.Collectors;

@RequiresRole(role = Role.CHEF)
@SpringView(name = "add_pizza")
public class AddPizza extends EditPizza{
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        loadItemsIntoIngredientsTCS();
        setBaseSauceComboBoxItemsAndDefaultValue();
        confirmBtn.setCaption("Add");
        item = new Pizza();
    }

    private void setBaseSauceComboBoxItemsAndDefaultValue(){
        List<Ingredient> baseSauces = pizzaService.getIngredients().stream()
                .filter(ingredient -> ingredient.getType().equals(IngredientType.PIZZA_BASESAUCE))
                .collect(Collectors.toList());

        if(baseSauces.isEmpty()){
            return;
        }

        basesauceCB.setItems(baseSauces);
        basesauceCB.setItemCaptionGenerator(Ingredient::getName);
        basesauceCB.setValue(baseSauces.get(0));
    }

    @Override
    protected void onValidationSucceededAfterConfirmButtonPressed() {
        MainView ui = (MainView)getUI();
        User loggedInUser = ui.getLoggedInUser()
                .orElseThrow(() -> new UnsupportedOperationException("Need to be logged in to add pizza!"));
        pizzaService.savePizza(item, loggedInUser.getEmailAddress());

        ui.getNavigator().navigateTo("pizza_editor");
        Notification.show("Successfully added pizza.", Notification.Type.WARNING_MESSAGE);
    }
}
