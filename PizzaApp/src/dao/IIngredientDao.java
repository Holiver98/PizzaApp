package dao;

import model.Ingredient;
import model.IngredientType;

import java.util.List;

public interface IIngredientDao {
    void saveIngredient(Ingredient ingredient);
    List<Ingredient> getIngredients();
    Ingredient getIngredientByName(String name);
    List<Ingredient> getIngredientsOfType(IngredientType type);
    void updateIngredient(Ingredient ingredient);
    void deleteIngredient(Ingredient ingredient);
}