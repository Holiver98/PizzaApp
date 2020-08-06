package com.github.holiver98.dal.inmemory;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.IngredientType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryIngredientDao implements IInMemoryIngredientDao {

    private List<Ingredient> ingredients = new ArrayList<Ingredient>();

    @Override
    public void saveIngredient(Ingredient ingredient) {
        if(ingredient == null){
            return;
        }

        Optional<Ingredient> dbIngredient = getIngredientByName(ingredient.getName());
        if(dbIngredient.isPresent()){
            //Already in database
            return;
        }

        ingredients.add(ingredient);
    }

    @Override
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public Optional<Ingredient> getIngredientByName(String name) {
        return ingredients.stream()
                .filter(i -> i.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Ingredient> getIngredientsOfType(IngredientType type) {
        return ingredients.stream()
                .filter(i -> i.getType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public void updateIngredient(Ingredient ingredient) {
        if(ingredient == null){
            return;
        }

        Optional<Ingredient> oldIngredient = getIngredientByName(ingredient.getName());
        oldIngredient.ifPresent(o -> updateOldIngredientWithNew(o, ingredient));
    }

    @Override
    public void deleteIngredient(String ingredientName) {
        Optional<Ingredient> dbIngredient = getIngredientByName(ingredientName);
        dbIngredient.ifPresent(i -> ingredients.remove(i));
    }

    private void updateOldIngredientWithNew(Ingredient oldIngredient, Ingredient newIngredient){
        oldIngredient.setName(newIngredient.getName());
        oldIngredient.setPrice(newIngredient.getPrice());
        oldIngredient.setType(newIngredient.getType());
    }
}
