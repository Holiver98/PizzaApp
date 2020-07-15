package dao;

import model.Ingredient;
import model.IngredientType;

import java.util.List;

public interface IIngredientDao {
    /**
     * Saves the ingredient into database.
     *
     * @param ingredient The id the database generated for the ingredient.
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
    Ingredient getIngredientByName(String name);

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
     * @param ingredient The ingredient to be deleted.
     */
    void deleteIngredient(Ingredient ingredient);
}