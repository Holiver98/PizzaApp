package com.github.holiver98.dal.inmemory;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.IngredientType;

import java.util.List;
import java.util.Optional;

//TODO: Interfészbe nem szokás az implementációt feltüntetni. IIngredientDao, aminek vannak bizonyos műveletei, és nem mellesleg készült hozzá egy InMemory
// implementáció is, de készülhetett volna fájl alapú, vagy akár jdbc is.
public interface IInMemoryIngredientDao {
    /**
     * Saves the ingredient into database.
     *
     * @param ingredient The ingredient to be saved.
     */
    //TODO: általában mindig jó ha van visszatérési érték, akár csak annyi hogy sikeres/sikertelen volt a művelet.
	//Mivel nincs érdemi "válasz" a kérésre, ezért azt nem lehet kideríteni hogy azért nem volt mentés mert már el van mentve.
	// A te esetedben miven nem lesz módosítva az eredi objektum, nem annyira érdekes most. De mondjuk
	// az elképzelhető lenne hogy bemenő paraméter a mentendő alapanyag, visszatérési érték pedig, a mentett alapanyag (kapott egy id-t, esetleg, ha van
	// collection használva akkor az valamilyen speciális implementációt kapott.
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