package com.github.holiver98.ui;

import com.github.holiver98.model.Pizza;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

@SpringView
public class EditPizzasItem extends HorizontalLayout implements View {
    public interface Listener{
        void onDeleteButtonPressed(Pizza item);
        void onEditButtonPressed(Pizza item);
    }

    private Pizza item;

    private List<Listener> listeners = new ArrayList<>();

    private Label idLabel;
    private Label nameLabel;
    private Button editBtn;
    private Button deleteBtn;

    public EditPizzasItem(){
        VerticalLayout idVL = new VerticalLayout();
        Label idCaptionLabel = new Label("Id:");
        idLabel = new Label("-");
        idVL.addComponent(idCaptionLabel);
        idVL.addComponent(idLabel);

        VerticalLayout nameVL = new VerticalLayout();
        Label nameCaptionLabel = new Label("Name:");
        nameLabel = new Label("-");
        nameVL.addComponent(nameCaptionLabel);
        nameVL.addComponent(nameLabel);

        editBtn = new Button("Edit");
        editBtn.addClickListener(clickEvent -> onEditButtonPressed());
        deleteBtn = new Button("Delete");
        deleteBtn.addClickListener(clickEvent -> onDeleteButtonPressed());

        addComponent(idVL);
        addComponent(nameVL);
        addComponent(editBtn);
        addComponent(deleteBtn);
    }

    public EditPizzasItem(Pizza pizza){
        this();
        setItem(pizza);
    }

    public void setItem(Pizza pizza){
        item = pizza;
        updateUiFields();
    }

    public void addListener(Listener listener){
        listeners.add(listener);
    }

    public void removeListener(Listener listener){
        listeners.remove(listener);
    }

    private void updateUiFields(){
        idLabel.setValue(item.getId().toString());
        nameLabel.setValue(item.getName());
    }

    private void onEditButtonPressed(){
        listeners.forEach(listener -> listener.onEditButtonPressed(item));
    }

    private void onDeleteButtonPressed(){
        listeners.forEach(listener -> listener.onDeleteButtonPressed(item));
    }
}
