package com.github.holiver98.ui;

import com.github.holiver98.model.Pizza;
import com.github.holiver98.service.IPizzaService;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringView(name = "edit_pizzas")
public class EditPizzas extends VerticalLayout implements View, EditPizzasItem.Listener {
    @Autowired
    private IPizzaService pizzaService;

    public EditPizzas(){

    }

    @PostConstruct
    private void afterInit(){
        List<Pizza> pizzas = pizzaService.getPizzas();
        for(Pizza pizza : pizzas){
            EditPizzasItem pizzaItemUi = new EditPizzasItem(pizza);
            pizzaItemUi.addListener(this);
            addComponent(pizzaItemUi);
        }
    }

    @Override
    public void onDeleteButtonPressed(Pizza item) {
        Notification.show("delete pressed: " + item);
    }

    @Override
    public void onEditButtonPressed(Pizza item) {
        String path = "edit_pizzas_details/" + item.getId();
        getUI().getNavigator().navigateTo(path);
    }
}
