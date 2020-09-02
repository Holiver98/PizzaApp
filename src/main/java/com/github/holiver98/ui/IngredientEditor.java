package com.github.holiver98.ui;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.Role;
import com.github.holiver98.model.User;
import com.github.holiver98.service.IPizzaService;
import com.github.holiver98.util.RequiresRole;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiresRole(role = Role.CHEF)
@SpringView(name = "ingredient_editor")
public class IngredientEditor extends VerticalLayout implements View, EditIngredientsItem.Listener {
    @Autowired
    private IPizzaService pizzaService;

    private Button addBtn;

    public IngredientEditor(){
        addBtn = new Button("Add new");
        addBtn.addClickListener(clickEvent -> getUI().getNavigator().navigateTo("add_ingredient"));
    }

    @PostConstruct
    private void afterInit(){
        List<Ingredient> ingredients = pizzaService.getIngredients();
        for(Ingredient ingredient : ingredients){
            EditIngredientsItem ingredientItemUi = new EditIngredientsItem(ingredient);
            ingredientItemUi.addListener(this);
            addComponent(ingredientItemUi);
        }
        addComponent(addBtn);
    }

    @Override
    public void onDeleteButtonPressed(Ingredient item, EditIngredientsItem clickedComponent) {
        User loggedInUser = ((MainView)getUI()).getLoggedInUser()
                .orElseThrow(() -> new NullPointerException("loggedInUser is null"));
        int deletionResult = pizzaService.deleteIngredient(item.getName(), loggedInUser.getEmailAddress());
        if(deletionResult == 1){
            clickedComponent.removeListener(this);
            removeComponent(clickedComponent);
            Notification.show("Item deleted successfully.", Notification.Type.WARNING_MESSAGE);
        }else if(deletionResult == -1){
            Notification.show("Deletion unsuccessful. Another pizza is using this item.", Notification.Type.WARNING_MESSAGE);
        }
    }

    @Override
    public void onEditButtonPressed(Ingredient item, EditIngredientsItem clickedComponent) {
        String path = "edit_ingredient/" + item.getName();
        getUI().getNavigator().navigateTo(path);
    }
}
