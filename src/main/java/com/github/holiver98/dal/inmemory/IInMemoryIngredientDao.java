package com.github.holiver98.dal.inmemory;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.IngredientType;

import java.util.List;
import java.util.Optional;

//TODO: Interfészbe nem szokás az implementációt feltüntetni. IIngredientDao, aminek vannak bizonyos műveletei, és nem mellesleg készült hozzá egy InMemory
// implementáció is, de készülhetett volna fájl alapú, vagy akár jdbc is.
public interface IInMemoryIngredientDao {
    //TODO: általában mindig jó ha van visszatérési érték, akár csak annyi hogy sikeres/sikertelen volt a művelet.
	//Mivel nincs érdemi "válasz" a kérésre, ezért azt nem lehet kideríteni hogy azért nem volt mentés mert már el van mentve.
	// A te esetedben miven nem lesz módosítva az eredi objektum, nem annyira érdekes most. De mondjuk
	// az elképzelhető lenne hogy bemenő paraméter a mentendő alapanyag, visszatérési érték pedig, a mentett alapanyag (kapott egy id-t, esetleg, ha van
	// collection használva akkor az valamilyen speciális implementációt kapott.

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