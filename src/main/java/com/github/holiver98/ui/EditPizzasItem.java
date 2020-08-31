package com.github.holiver98.ui;

import com.github.holiver98.model.Pizza;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

@SpringView
public class EditPizzasItem extends HorizontalLayout implements View {
    public interface Listener{
        void onDeleteButtonPressed(Pizza item, EditPizzasItem clickedComponent);
        void onEditButtonPressed(Pizza item, EditPizzasItem clickedComponent);
    }

    private Pizza item;

    private List<Listener> listeners = new ArrayList<>();
    private List<Listener> newlyUnsubscribedListeners = new ArrayList<>();

    private Label idLabel;
    private Label nameLabel;
    private Button editBtn;
    private Button deleteBtn;

    public EditPizzasItem(){
        setStyleName("editPizzasItem");

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
        deleteBtn.setStyleName("removeBtn");
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
        newlyUnsubscribedListeners.add(listener);
    }

    private void updateUiFields(){
        idLabel.setValue(item.getId().toString());
        nameLabel.setValue(item.getName());
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
