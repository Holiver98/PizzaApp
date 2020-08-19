package com.github.holiver98.ui;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.IngredientType;
import com.github.holiver98.service.ICartService;
import com.github.holiver98.service.IPizzaService;
import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringView(name = "custom")
public class Custom extends HorizontalLayout implements View {
    private static final String totalPriceLabelBaseText = "Total price: ";

    @Autowired
    private ICartService cartService;
    @Autowired
    private IPizzaService pizzaService;

    private VerticalLayout ingredientList;
    private ComboBox<String> basesauceCB;
    private VerticalLayout selectedToppingsVL;
    private Label totalPriceLabel;

    private Set<Ingredient> selectedToppings = new HashSet<>();

    public Custom(){
        VerticalLayout leftLayout = new VerticalLayout();
        Panel rightPanel = new Panel();
        rightPanel.setHeight("400");
        ingredientList = new VerticalLayout();
        rightPanel.setContent(ingredientList);

        addComponent(leftLayout);
        addComponent(rightPanel);

        basesauceCB = new ComboBox<>("Base sauce");
        basesauceCB.setEmptySelectionAllowed(false);
        basesauceCB.setTextInputAllowed(false);
        basesauceCB.setItems("Tomato", "Sour cream");
        basesauceCB.setValue("Tomato");

        selectedToppingsVL = new VerticalLayout();
        selectedToppingsVL.setCaption("Toppings");
        totalPriceLabel = new Label(totalPriceLabelBaseText + "0.00$");
        Button addToCartBtn = new Button("Add to cart");
        addToCartBtn.addClickListener(clickEvent -> createCustomPizzaAndAddtoCart());

        leftLayout.addComponent(basesauceCB);
        leftLayout.addComponent(selectedToppingsVL);
        leftLayout.addComponent(totalPriceLabel);
        leftLayout.addComponent(addToCartBtn);
    }

    private void createCustomPizzaAndAddtoCart() {
        /*Pizza pizza = new Pizza();
        pizza.setName("Not Implemented");
        pizza.setCustom(true);
        Set<Ingredient> ing = new HashSet<>();
        Ingredient i = new Ingredient();
        i.setName("nifdsd");
        i.setType(IngredientType.PIZZA_BASESAUCE);
        ing.add(i);
        Ingredient i2 = new Ingredient();
        i2.setName("dsafds");
        i2.setType(IngredientType.PIZZA_TOPPING);
        ing.add(i2);
        pizza.setIngredients(ing);
        pizza.setPrice(BigDecimal.valueOf(22.1));
        pizza.setSize(PizzaSize.NORMAL);
        pizza.setRatingAverage(BigDecimal.valueOf(3.2));

        try {
            cartService.addPizzaToCart(pizza);
        } catch (CartIsFullException e) {
            e.printStackTrace();
            Notification.show("full");
        }*/
    }

    @PostConstruct
    private void afterInit(){
        basesauceCB.addValueChangeListener(valueChangeEvent -> recalculateTotalPrice());
        recalculateTotalPrice();
        loadIngredients();
    }

    private void loadIngredients() {
        List<Ingredient> ingredients = pizzaService.getIngredients();
        for(Ingredient ingredient: ingredients){
            boolean isBaseSauce = ingredient.getType().equals(IngredientType.PIZZA_BASESAUCE);
            if(isBaseSauce){
                continue;
            }

            ItemCard item = new ItemCard(ingredient.getName());
            item.setImage(new ThemeResource("images/ingredientTest.jpg"));
            item.setHeight("200");
            item.setWidth("200");
            item.addLayoutClickListener(layoutClickEvent -> addToSelectedToppings(ingredient));
            ingredientList.addComponent(item);
        }
    }

    private void addToSelectedToppings(Ingredient ingredient) {
        boolean didntContain = selectedToppings.add(ingredient);
        if(didntContain){
            selectedToppingsVL.addComponent(new Label(ingredient.getName()));
            recalculateTotalPrice();
        }
    }

    private void recalculateTotalPrice() {
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for(Ingredient ingredient : selectedToppings){
            boolean isBaseSauce = ingredient.getType().equals(IngredientType.PIZZA_BASESAUCE);
            if(isBaseSauce){
                continue;
            }

            totalPrice = totalPrice.add(ingredient.getPrice());
        }

        String selectedBaseSauceName = basesauceCB.getValue();
        Optional<Ingredient> optionalIngredient = pizzaService.getPizzaIngredientByName(selectedBaseSauceName);
        Ingredient selectedBaseSauce = optionalIngredient
                .orElseThrow(() -> new NullPointerException("No Ingredient exists with such name, there might have been a typo in the base sauce comboBox!"));
        totalPrice = totalPrice.add(selectedBaseSauce.getPrice());

        totalPriceLabel.setValue(totalPriceLabelBaseText + totalPrice.toString() + "$");
    }
}
