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
    public Optional<Ingredient> saveIngredient(Ingredient ingredient) {
        if(ingredient == null){
            throw new NullPointerException("ingredient is null");
        }

        Optional<Ingredient> dbIngredient = getIngredientByName(ingredient.getName());
        if(dbIngredient.isPresent()){
            //Already in database
            return Optional.empty();
        }

        ingredients.add(ingredient);
        return Optional.of(ingredient);
    }

    @Override
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public Optional<Ingredient> getIngredientByName(String name) {
        if(name == null){
            throw new NullPointerException("ingredient name is null");
        }
        return ingredients.stream()
                .filter(i -> i.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Ingredient> getIngredientsOfType(IngredientType type) {
        if(type == null){
            throw new NullPointerException("ingredient type is null");
        }
        return ingredients.stream()
                .filter(i -> i.getType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public int updateIngredient(Ingredient ingredient) {
        if(ingredient == null){
            throw new NullPointerException("ingredient is null");
        }

        Optional<Ingredient> oldIngredient = getIngredientByName(ingredient.getName());
        if(oldIngredient.isPresent()){
            updateOldIngredientWithNew(oldIngredient.get(), ingredient);
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public int deleteIngredient(String ingredientName) {
        if(ingredientName == null){
            throw new NullPointerException("ingredientName is null");
        }
        Optional<Ingredient> dbIngredient = getIngredientByName(ingredientName);
        if(dbIngredient.isPresent()){
            ingredients.remove(dbIngredient.get());
            return 1;
        }else{
            return -1;
        }
    }

    private void updateOldIngredientWithNew(Ingredient oldIngredient, Ingredient newIngredient){
        oldIngredient.setName(newIngredient.getName());
        oldIngredient.setPrice(newIngredient.getPrice());
        oldIngredient.setType(newIngredient.getType());
    }
}
