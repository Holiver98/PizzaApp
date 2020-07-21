package dao;

import model.Ingredient;
import model.IngredientType;

import java.util.List;
import java.util.Optional;

public interface IIngredientDao {
    /**
     * Saves the ingredient into database.
     *
     * @param ingredient The ingredient to be saved.
     */
    void saveIngredient(Ingredient ingredient);

    /**
     * Gets all the ingredients from the database.
     *
     * @return A list of all the ingredients.
     */
    List<Ingredient> getIngredients();

    /**
     * Gets an ingredient by it's name from the database.
     *
     * @param name The name of the ingredient.
     * @return The ingredient with the given name, or null, if it doesn't exist in the database.
     */
    Optional<Ingredient> getIngredientByName(String name);

    /**
     * Gets all the ingredients from the database, that have the given type.
     *
     * @param type The type of the ingredient.
     * @return A list of the ingredients, that have the given type.
     */
    List<Ingredient> getIngredientsOfType(IngredientType type);

    /**
     * Updates the ingredient in the database that has the same id, as the given ingredient argument.
     *
     * @param ingredient The ingredient to be updated.
     */
    void updateIngredient(Ingredient ingredient);

    /**
     * Deletes the ingredient from the database, if it exists.
     *
     * @param ingredientName The name of the ingredient to be deleted.
     */
    void deleteIngredient(String ingredientName);
}