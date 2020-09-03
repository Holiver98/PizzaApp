package com.github.holiver98.ui;

import com.github.holiver98.model.Pizza;
import com.github.holiver98.service.IPizzaService;
import com.vaadin.navigator.View;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = "")
public class Home extends VerticalLayout implements View {
    @Autowired
    IPizzaService pizzaService;

    private CssLayout pizzaList;

    public Home(){
        Label title = new Label("PizzaApp");
        title.setStyleName("title");
        addComponent(title);
        setComponentAlignment(title, Alignment.MIDDLE_CENTER);
        CssLayout mottoContainer = new CssLayout();
        mottoContainer.setStyleName("mottoContainer");
        Label motto = new Label("Insert some cheesy, inspirational or funny motto here.");
        motto.setStyleName("motto");
        mottoContainer.addComponent(motto);
        addComponent(mottoContainer);
        setComponentAlignment(mottoContainer, Alignment.MIDDLE_CENTER);

        Label separator =  new Label("<hr />", ContentMode.HTML);
        addComponent(separator);
        separator.setWidth(80, Sizeable.Unit.PERCENTAGE);
        setComponentAlignment(separator, Alignment.MIDDLE_CENTER);

        pizzaList = new CssLayout();
        pizzaList.setStyleName("pizzalist");
        for(int i=0; i<15; i++){
            pizzaList.addComponent(new ItemCard("Pizza"));
        }
        addComponent(pizzaList);
    }

    @PostConstruct
    private void afterInit(){
        loadPizzas();
    }

    private void loadPizzas() {
        pizzaService.getBasicNonLegacyPizzas()
                .forEach(pizza -> addToPizzaList(pizza));
    }

    private void addToPizzaList(Pizza pizza) {
        ItemHolderCard<Pizza> uiItem = new ItemHolderCard<>(pizza);
        uiItem.setCaption(pizza.getName());
        uiItem.setImage(new ThemeResource("images/pizzaTest.jpg"));
        String pizzaDetailsViewPath = "pizza/" + pizza.getId();
        uiItem.addLayoutClickListener(layoutClickEvent -> getUI().getNavigator().navigateTo(pizzaDetailsViewPath));
        pizzaList.addComponent(uiItem);
    }
}
