package com.github.holiver98.dal.inmemory;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.IngredientType;

import java.util.List;
import java.util.Optional;

public interface IInMemoryIngredientDao {
    /**
     * @return The saved ingredient with it's id set. If ingredient already exists, null is returned.
     * @throws NullPointerException if ingredient is null.
     */
    Optional<Ingredient> saveIngredient(Ingredient ingredient);

    List<Ingredient> getIngredients();

    /**
     * @return The ingredient with the given name, or null, if it doesn't exist.
     * @throws NullPointerException if name is null.
     */
    Optional<Ingredient> getIngredientByName(String name);

    List<Ingredient> getIngredientsOfType(IngredientType type);

    /**
     * Updates the ingredient, that has the same id, as the given ingredient argument, with
     * the values of the ingredient argument.
     *
     * @param ingredient The ingredient to be updated, with the new values.
     * @return 1 - success, -1 - ingredient doesn't exist.
     * @throws NullPointerException if ingredient is null.
     */
    int updateIngredient(Ingredient ingredient);

    /**
     * Deletes the ingredient.
     *
     * @param ingredientName The name of the ingredient to be deleted.
     * @return 1 - success, -1 - ingredient doesn't exist with this name.
     * @throws NullPointerException if ingredientName is null.
     */
    int deleteIngredient(String ingredientName);
}