package com.github.holiver98.ui;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.IngredientType;
import com.github.holiver98.model.Pizza;
import com.github.holiver98.model.PizzaSize;
import com.github.holiver98.service.CartIsFullException;
import com.github.holiver98.service.ICartService;
import com.github.holiver98.service.IPizzaService;
import com.github.holiver98.util.ObservableHashSet;
import com.vaadin.event.LayoutEvents;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@SpringView(name = "custom")
public class Custom extends HorizontalLayout implements View, ObservableHashSet.ObservableHashSetListener {
    private static final String totalPriceLabelBaseText = "Total price: ";

    @Autowired
    private ICartService cartService;
    @Autowired
    private IPizzaService pizzaService;

    private VerticalLayout availableIngredientsVL;
    private ComboBox<Ingredient> basesauceCB;
    private ComboBox<PizzaSize> pizzaSizeCB;
    private VerticalLayout selectedToppingsVL;
    private Label totalPriceLabel;
    private Button addToCartBtn;

    private Set<Ingredient> selectedToppings;

    public Custom(){
        setStyleName("custom");

        ObservableHashSet<Ingredient> list = new ObservableHashSet<>();
        list.setListener(this);
        selectedToppings = list;

        VerticalLayout leftLayout = new VerticalLayout();
        Panel rightPanel = new Panel();
        rightPanel.setHeight("400");
        availableIngredientsVL = new VerticalLayout();
        rightPanel.setContent(availableIngredientsVL);

        addComponent(leftLayout);
        addComponent(rightPanel);
        rightPanel.setStyleName("rightPanel");
        setComponentAlignment(leftLayout, Alignment.MIDDLE_CENTER);
        setComponentAlignment(rightPanel, Alignment.TOP_CENTER);

        pizzaSizeCB = new ComboBox<>();
        pizzaSizeCB.setCaption("Size:");
        pizzaSizeCB.setEmptySelectionAllowed(false);
        pizzaSizeCB.setTextInputAllowed(false);
        pizzaSizeCB.setItems(EnumSet.allOf(PizzaSize.class));
        pizzaSizeCB.setValue(PizzaSize.NORMAL);

        basesauceCB = new ComboBox<>("Base sauce");
        basesauceCB.setEmptySelectionAllowed(false);
        basesauceCB.setTextInputAllowed(false);

        selectedToppingsVL = new VerticalLayout();
        selectedToppingsVL.setCaption("Toppings");
        selectedToppingsVL.setStyleName("selectedToppingsVL");
        totalPriceLabel = new Label(totalPriceLabelBaseText + "0.00$");
        addToCartBtn = new Button("Add to cart");
        addToCartBtn.setEnabled(false);
        addToCartBtn.addClickListener(clickEvent -> createCustomPizzaAndAddToCart());

        leftLayout.addComponent(basesauceCB);
        leftLayout.addComponent(pizzaSizeCB);
        leftLayout.addComponent(selectedToppingsVL);
        leftLayout.addComponent(totalPriceLabel);
        leftLayout.addComponent(addToCartBtn);
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

    private void createCustomPizzaAndAddToCart() {
        Pizza pizza = new Pizza();
        pizza.setName("Custom pizza");
        pizza.setCustom(true);
        HashSet<Ingredient> ingredients = new HashSet<>(selectedToppings);
        ingredients.add(getSelectedBaseSauce());
        pizza.setIngredients(ingredients);
        pizza.setSize(pizzaSizeCB.getValue());
        pizza.setPrice(calculateTotalPrice());
        pizza.setRatingAverage(BigDecimal.valueOf(0));

        try {
            Pizza savedPizza = pizzaService.saveCustomPizzaWithoutAuthentication(pizza);
            ((MainView)getUI()).addPizzaToCart(savedPizza);
            Notification.show("Pizza added to the cart.", Notification.Type.WARNING_MESSAGE);
        } catch (CartIsFullException e) {
            Notification.show("The cart is full!", Notification.Type.WARNING_MESSAGE);
        }
    }

    @PostConstruct
    private void afterInit(){
        basesauceCB.addValueChangeListener(valueChangeEvent -> calculateAndUpdateTotalPriceOnUi());
        setBaseSauceComboBoxItemsAndDefaultValue();
        loadToppings();
    }

    private void loadToppings() {
        List<Ingredient> ingredients = pizzaService.getIngredients();
        for(Ingredient ingredient: ingredients){
            boolean isBaseSauce = ingredient.getType().equals(IngredientType.PIZZA_BASESAUCE);
            if(isBaseSauce){
                continue;
            }

            addIngredientToAvailableIngredientsLayout(ingredient);
        }
    }

    private void addIngredientToAvailableIngredientsLayout(Ingredient ingredient){
        ItemCard item = new ItemCard(ingredient.getName());
        item.setImage(new ThemeResource("images/ingredientTest.jpg"));
        item.setHeight("200");
        item.setWidth("200");
        item.addLayoutClickListener(layoutClickEvent -> addToSelectedToppings(ingredient, layoutClickEvent));
        availableIngredientsVL.addComponent(item, 0);
    }

    private void addToSelectedToppings(Ingredient ingredient, LayoutEvents.LayoutClickEvent layoutClickEvent) {
        boolean didntContain = selectedToppings.add(ingredient);
        if(didntContain){
            addToSelectedToppingsLayout(ingredient);
            Component clickedComponent = layoutClickEvent.getComponent();
            availableIngredientsVL.removeComponent(clickedComponent);
            calculateAndUpdateTotalPriceOnUi();
        }
    }

    private void addToSelectedToppingsLayout(Ingredient ingredient) {
        HorizontalLayout container = new HorizontalLayout();
        container.addComponent(new Label(ingredient.getName()));
        Button removeBtn = new Button();
        removeBtn.setIcon(VaadinIcons.CLOSE_SMALL);
        removeBtn.addClickListener(clickEvent -> onIngredientRemoveButtonPressed(container, ingredient));
        container.addComponent(removeBtn);
        selectedToppingsVL.addComponent(container);
    }

    private void onIngredientRemoveButtonPressed(Component ingredientUiItem, Ingredient ingredient){
        selectedToppingsVL.removeComponent(ingredientUiItem);
        selectedToppings.remove(ingredient);
        addIngredientToAvailableIngredientsLayout(ingredient);
        calculateAndUpdateTotalPriceOnUi();
    }

    private BigDecimal calculateTotalPrice() {
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for(Ingredient ingredient : selectedToppings){
            boolean isBaseSauce = ingredient.getType().equals(IngredientType.PIZZA_BASESAUCE);
            if(isBaseSauce){
                continue;
            }

            totalPrice = totalPrice.add(ingredient.getPrice());
        }

        totalPrice = totalPrice.add(getSelectedBaseSauce().getPrice());

        return totalPrice;
    }

    private void calculateAndUpdateTotalPriceOnUi(){
        BigDecimal totalPrice = calculateTotalPrice();
        totalPriceLabel.setValue(totalPriceLabelBaseText + totalPrice.toString() + "$");
    }

    private Ingredient getSelectedBaseSauce(){
       return basesauceCB.getValue();
    }

    @Override
    public void onItemAdded(Object item) {
        if(hasValidAmountOfToppings()){
            addToCartBtn.setEnabled(true);
        }else{
            addToCartBtn.setEnabled(false);
        }
    }

    @Override
    public void onItemRemoved(Object item) {
        if(hasValidAmountOfToppings()){
            addToCartBtn.setEnabled(true);
        }else{
            addToCartBtn.setEnabled(false);
        }
    }

    private boolean hasValidAmountOfToppings(){
        if(selectedToppings.size() < 1 || selectedToppings.size() > 5){
            return false;
        }else{
            return true;
        }
    }
}
