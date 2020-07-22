package com.github.holiver98.dao;

import com.github.holiver98.database.InMemoryDatabase;
import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.IngredientType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryIngredientDao implements IIngredientDao {

    private InMemoryDatabase dbContext;

    public InMemoryIngredientDao(InMemoryDatabase context){
        dbContext = context;
    }

    @Override
    public void saveIngredient(Ingredient ingredient) {
        if(ingredient == null){
            return;
        }

        Optional<Ingredient> dbIngredient = getIngredientByName(ingredient.getName());
        if(dbIngredient.isPresent()){
            //Already in com.github.holiver98.database
            return;
        }

        dbContext.ingredients.add(ingredient);
    }

    @Override
    public List<Ingredient> getIngredients() {
        return dbContext.ingredients;
    }

    @Override
    public Optional<Ingredient> getIngredientByName(String name) {
        return dbContext.ingredients.stream()
                .filter(i -> i.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Ingredient> getIngredientsOfType(IngredientType type) {
        return dbContext.ingredients.stream()
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
        dbIngredient.ifPresent(i -> dbContext.ingredients.remove(i));
    }

    private void updateOldIngredientWithNew(Ingredient oldIngredient, Ingredient newIngredient){
        oldIngredient.setName(newIngredient.getName());
        oldIngredient.setPrice(newIngredient.getPrice());
        oldIngredient.setType(newIngredient.getType());
    }
}
