package com.github.holiver98.ui;

import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.User;
import com.github.holiver98.service.IPizzaService;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringView(name = "pizza_editor")
public class PizzaEditor extends VerticalLayout implements View, EditPizzasItem.Listener {
    @Autowired
    private IPizzaService pizzaService;

    private Button addBtn;

    public PizzaEditor(){
        addBtn = new Button("Add new");
        addBtn.addClickListener(clickEvent -> getUI().getNavigator().navigateTo("add_pizza"));
    }

    @PostConstruct
    private void afterInit(){
        List<Pizza> pizzas = pizzaService.getBasicNonLegacyPizzas();
        for(Pizza pizza : pizzas){
            EditPizzasItem pizzaItemUi = new EditPizzasItem(pizza);
            pizzaItemUi.addListener(this);
            addComponent(pizzaItemUi);
        }
        addComponent(addBtn);
    }

    @Override
    public void onDeleteButtonPressed(Pizza item, EditPizzasItem clickedComponent) {
        User loggedInUser = ((MainView)getUI()).getLoggedInUser()
                .orElseThrow(() -> new NullPointerException("loggedInUser is null"));
        int deletionResult = pizzaService.deletePizza(item.getId(), loggedInUser.getEmailAddress());
        if(deletionResult == 1){
            clickedComponent.removeListener(this);
            removeComponent(clickedComponent);
            Notification.show("Item deleted successfully.", Notification.Type.WARNING_MESSAGE);
        }
    }

    @Override
    public void onEditButtonPressed(Pizza item, EditPizzasItem clickedComponent) {
        String path = "edit_pizza/" + item.getId();
        getUI().getNavigator().navigateTo(path);
    }
}
