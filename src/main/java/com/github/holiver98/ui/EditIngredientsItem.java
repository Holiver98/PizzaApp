package com.github.holiver98.ui;

import com.github.holiver98.model.Ingredient;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

@SpringView
public class EditIngredientsItem extends HorizontalLayout implements View {
    public interface Listener{
        void onDeleteButtonPressed(Ingredient item, EditIngredientsItem clickedComponent);
        void onEditButtonPressed(Ingredient item, EditIngredientsItem clickedComponent);
    }

    private Ingredient item;

    private List<Listener> listeners = new ArrayList<>();
    private List<Listener> newlyUnsubscribedListeners = new ArrayList<>();

    private Label nameLabel;
    private Label priceLabel;

    public EditIngredientsItem(){
        setStyleName("editIngredientsItem");

        VerticalLayout nameVL = new VerticalLayout();
        Label nameCaptionLabel = new Label("Name:");
        nameLabel = new Label("-");
        nameVL.addComponent(nameCaptionLabel);
        nameVL.addComponent(nameLabel);

        VerticalLayout priceVL = new VerticalLayout();
        Label priceCaptionLabel = new Label("Price:");
        priceLabel = new Label("-");
        priceVL.addComponent(priceCaptionLabel);
        priceVL.addComponent(priceLabel);

        Button editBtn = new Button("Edit");
        editBtn.addClickListener(clickEvent -> onEditButtonPressed());
        Button deleteBtn = new Button("Delete");
        deleteBtn.setStyleName("removeBtn");
        deleteBtn.addClickListener(clickEvent -> onDeleteButtonPressed());

        addComponent(nameVL);
        addComponent(priceVL);
        addComponent(editBtn);
        addComponent(deleteBtn);
    }

    public EditIngredientsItem(Ingredient ingredient){
        this();
        setItem(ingredient);
    }

    public void setItem(Ingredient ingredient){
        item = ingredient;
        updateUiFields();
    }

    public void addListener(Listener listener){
        listeners.add(listener);
    }

    public void removeListener(Listener listener){
        newlyUnsubscribedListeners.add(listener);
    }

    private void updateUiFields(){
        nameLabel.setValue(item.getName());
        priceLabel.setValue(item.getPrice().toString() + "$");
    }

    private void onEditButtonPressed(){
        listeners.forEach(listener -> listener.onEditButtonPressed(item, this));
    }

    private void onDeleteButtonPressed(){
        listeners.forEach(listener -> listener.onDeleteButtonPressed(item, this));
        removeNewlyUnsubscribedListeners();
    }

    private void removeNewlyUnsubscribedListeners(){
        newlyUnsubscribedListeners.forEach(listener ->listeners.remove(listener));
        newlyUnsubscribedListeners.clear();
    }
}
