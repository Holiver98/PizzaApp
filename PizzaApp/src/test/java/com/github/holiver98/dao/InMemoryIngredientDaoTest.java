package com.github.holiver98.dao;

import com.github.holiver98.database.InMemoryDatabase;
import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.IngredientType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class InMemoryIngredientDaoTest{

    private InMemoryDatabase database;
    private InMemoryIngredientDao ingredientDao;

    @BeforeEach
    void init(){
        database = new InMemoryDatabase();
        ingredientDao = new InMemoryIngredientDao(database);
    }

    @Test
    void saveIngredient_Passing_Null_Should_Not_Save(){
        //Arrange

        //Act
        ingredientDao.saveIngredient(null);

        //Assert
        assertThat(database.ingredients.size()).isEqualTo(0);
    }

    @Test
    void saveIngredient_Should_Save(){
        //Arrange
        Ingredient ingredient = new Ingredient();
        ingredient.setType(IngredientType.PIZZA_TOPPING);
        ingredient.setPrice(3.14f);
        ingredient.setName("Onion");

        //Act
        ingredientDao.saveIngredient(ingredient);

        //Assert
        assertThat(database.ingredients.size()).isEqualTo(1);
        assertThat(database.ingredients.contains(ingredient)).isEqualTo(true);
    }

    @Test
    void saveIngredient_Ingredient_Already_Exists_Should_Not_Save(){
        //Arrange
        Ingredient ingredient = new Ingredient();
        ingredient.setType(IngredientType.PIZZA_TOPPING);
        ingredient.setPrice(3.14f);
        ingredient.setName("Onion");

        database.ingredients.add(ingredient);

        //Act
        ingredientDao.saveIngredient(ingredient);

        //Assert
        assertThat(database.ingredients.size()).isEqualTo(1);
        assertThat(database.ingredients.contains(ingredient)).isEqualTo(true);
    }

    @Test
    void getIngredientByName_Passing_Null_Should_Return_Empty(){
        //Arrange
        Ingredient ingredient = new Ingredient();
        ingredient.setType(IngredientType.PIZZA_TOPPING);
        ingredient.setPrice(3.14f);
        ingredient.setName("Onion");

        database.ingredients.add(ingredient);

        //Act
        Optional<Ingredient> result = ingredientDao.getIngredientByName(null);

        //Assert
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    void getIngredientByName_Should_Return_The_Result(){
        //Arrange
        Ingredient onion = new Ingredient();
        onion.setType(IngredientType.PIZZA_TOPPING);
        onion.setPrice(3.14f);
        onion.setName("Onion");

        Ingredient tomato = new Ingredient();
        tomato.setType(IngredientType.PIZZA_BASESAUCE);
        tomato.setPrice(2.1f);
        tomato.setName("Tomato");

        Ingredient cheese = new Ingredient();
        cheese.setType(IngredientType.PIZZA_TOPPING);
        cheese.setPrice(4.5f);
        cheese.setName("Cheese");

        database.ingredients.add(onion);
        database.ingredients.add(tomato);
        database.ingredients.add(cheese);

        //Act
        Optional<Ingredient> result = ingredientDao.getIngredientByName("Tomato");

        //Assert
        assertThat(result.get()).isEqualTo(tomato);
    }

    @Test
    void getIngredientsOfType_Should_Return_Ingredients(){
        //Arrange
        Ingredient onion = new Ingredient();
        onion.setType(IngredientType.PIZZA_TOPPING);
        onion.setPrice(3.14f);
        onion.setName("Onion");

        Ingredient tomato = new Ingredient();
        tomato.setType(IngredientType.PIZZA_BASESAUCE);
        tomato.setPrice(2.1f);
        tomato.setName("Tomato");

        Ingredient cheese = new Ingredient();
        cheese.setType(IngredientType.PIZZA_TOPPING);
        cheese.setPrice(4.5f);
        cheese.setName("Cheese");

        database.ingredients.add(onion);
        database.ingredients.add(tomato);
        database.ingredients.add(cheese);

        //Act
        List<Ingredient> result = ingredientDao.getIngredientsOfType(IngredientType.PIZZA_TOPPING);

        //Assert
        assertThat(result.contains(onion)).isEqualTo(true);
        assertThat(result.contains(cheese)).isEqualTo(true);
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void getIngredientsOfType_Passing_Null_Should_Return_Empty_List(){
        //Arrange
        Ingredient onion = new Ingredient();
        onion.setType(IngredientType.PIZZA_TOPPING);
        onion.setPrice(3.14f);
        onion.setName("Onion");

        Ingredient tomato = new Ingredient();
        tomato.setType(IngredientType.PIZZA_BASESAUCE);
        tomato.setPrice(2.1f);
        tomato.setName("Tomato");

        Ingredient cheese = new Ingredient();
        cheese.setType(IngredientType.PIZZA_TOPPING);
        cheese.setPrice(4.5f);
        cheese.setName("Cheese");

        database.ingredients.add(onion);
        database.ingredients.add(tomato);
        database.ingredients.add(cheese);

        //Act
        List<Ingredient> result = ingredientDao.getIngredientsOfType(null);

        //Assert
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void getIngredientsOfType_No_Matching_Type_Should_Return_Empty_List(){
        //Arrange
        Ingredient onion = new Ingredient();
        onion.setType(IngredientType.PIZZA_TOPPING);
        onion.setPrice(3.14f);
        onion.setName("Onion");

        Ingredient cheese = new Ingredient();
        cheese.setType(IngredientType.PIZZA_TOPPING);
        cheese.setPrice(4.5f);
        cheese.setName("Cheese");

        database.ingredients.add(onion);
        database.ingredients.add(cheese);

        //Act
        List<Ingredient> result = ingredientDao.getIngredientsOfType(IngredientType.PIZZA_BASESAUCE);

        //Assert
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void getIngredientsOfType_Empty_Database_Table_Should_Return_Empty_List(){
        //Arrange

        //Act
        List<Ingredient> result = ingredientDao.getIngredientsOfType(IngredientType.PIZZA_BASESAUCE);

        //Assert
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void updateIngredient_Ingredient_Not_In_Database_Should_Not_Update(){
        //Arrange
        Ingredient onion = new Ingredient();
        onion.setType(IngredientType.PIZZA_TOPPING);
        onion.setPrice(3.14f);
        onion.setName("Onion");

        Ingredient initialOnion = new Ingredient();
        initialOnion.setType(onion.getType());
        initialOnion.setPrice(onion.getPrice());
        initialOnion.setName(onion.getName());

        Ingredient cheese = new Ingredient();
        cheese.setType(IngredientType.PIZZA_TOPPING);
        cheese.setPrice(4.5f);
        cheese.setName("Cheese");

        database.ingredients.add(cheese);

        //Act
        ingredientDao.updateIngredient(onion);

        //Assert
        assertThat(database.ingredients.size()).isEqualTo(1);
        assertThat(onion).isEqualTo(initialOnion);
    }

    @Test
    void updateIngredient_Ingredient_In_Database_Should_Update(){
        //Arrange
        Ingredient onion = new Ingredient();
        onion.setType(IngredientType.PIZZA_TOPPING);
        onion.setPrice(3.14f);
        onion.setName("Onion");

        Ingredient cheese = new Ingredient();
        cheese.setType(IngredientType.PIZZA_TOPPING);
        cheese.setPrice(4.5f);
        cheese.setName("Cheese");

        database.ingredients.add(onion);
        database.ingredients.add(cheese);

        Ingredient newOnion = new Ingredient();
        newOnion.setType(IngredientType.PIZZA_TOPPING);
        newOnion.setPrice(4.14f);
        newOnion.setName("Onion");

        //Act
        ingredientDao.updateIngredient(newOnion);

        //Assert
        assertThat(database.ingredients.size()).isEqualTo(2);
        assertThat(onion).isEqualTo(newOnion);
    }

    @Test
    void updateIngredient_Ingredient_In_Database_Should_Not_Update_Something_Else_Too(){
        //Arrange
        Ingredient onion = new Ingredient();
        onion.setType(IngredientType.PIZZA_TOPPING);
        onion.setPrice(3.14f);
        onion.setName("Onion");

        Ingredient cheese = new Ingredient();
        cheese.setType(IngredientType.PIZZA_TOPPING);
        cheese.setPrice(4.5f);
        cheese.setName("Cheese");

        Ingredient initialCheese = new Ingredient();
        initialCheese.setType(cheese.getType());
        initialCheese.setPrice(cheese.getPrice());
        initialCheese.setName(cheese.getName());

        database.ingredients.add(onion);
        database.ingredients.add(cheese);

        Ingredient newOnion = new Ingredient();
        newOnion.setType(IngredientType.PIZZA_TOPPING);
        newOnion.setPrice(4.14f);
        newOnion.setName("Onion");

        //Act
        ingredientDao.updateIngredient(newOnion);

        //Assert
        assertThat(cheese).isEqualTo(initialCheese);
    }

    @Test
    void updateIngredient_Passing_Null_Should_Not_Update_Anything(){
        //Arrange
        Ingredient onion = new Ingredient();
        onion.setType(IngredientType.PIZZA_TOPPING);
        onion.setPrice(3.14f);
        onion.setName("Onion");

        Ingredient initialOnion = new Ingredient();
        initialOnion.setType(onion.getType());
        initialOnion.setPrice(onion.getPrice());
        initialOnion.setName(onion.getName());

        database.ingredients.add(onion);

        //Act
        ingredientDao.updateIngredient(null);

        //Assert
        assertThat(database.ingredients.size()).isEqualTo(1);
        assertThat(onion).isEqualTo(initialOnion);
    }

    @Test
    void deleteIngredient_Should_Be_Deleted(){
        //Arrange
        Ingredient onion = new Ingredient();
        onion.setType(IngredientType.PIZZA_TOPPING);
        onion.setPrice(3.14f);
        onion.setName("Onion");

        Ingredient tomato = new Ingredient();
        tomato.setType(IngredientType.PIZZA_BASESAUCE);
        tomato.setPrice(2.1f);
        tomato.setName("Tomato");

        Ingredient cheese = new Ingredient();
        cheese.setType(IngredientType.PIZZA_TOPPING);
        cheese.setPrice(4.5f);
        cheese.setName("Cheese");

        database.ingredients.add(onion);
        database.ingredients.add(tomato);
        database.ingredients.add(cheese);

        //Act
        ingredientDao.deleteIngredient("Tomato");

        //Assert
        assertThat(database.ingredients.contains(onion)).isEqualTo(true);
        assertThat(database.ingredients.contains(tomato)).isEqualTo(false);
        assertThat(database.ingredients.contains(cheese)).isEqualTo(true);
        assertThat(database.ingredients.size()).isEqualTo(2);
    }

    @Test
    void deleteIngredient_Passing_Null_Should_Not_Delete(){
        //Arrange
        Ingredient onion = new Ingredient();
        onion.setType(IngredientType.PIZZA_TOPPING);
        onion.setPrice(3.14f);
        onion.setName("Onion");

        Ingredient tomato = new Ingredient();
        tomato.setType(IngredientType.PIZZA_BASESAUCE);
        tomato.setPrice(2.1f);
        tomato.setName("Tomato");

        Ingredient cheese = new Ingredient();
        cheese.setType(IngredientType.PIZZA_TOPPING);
        cheese.setPrice(4.5f);
        cheese.setName("Cheese");

        database.ingredients.add(onion);
        database.ingredients.add(tomato);
        database.ingredients.add(cheese);

        //Act
        ingredientDao.deleteIngredient(null);

        //Assert
        assertThat(database.ingredients.contains(onion)).isEqualTo(true);
        assertThat(database.ingredients.contains(tomato)).isEqualTo(true);
        assertThat(database.ingredients.contains(cheese)).isEqualTo(true);
        assertThat(database.ingredients.size()).isEqualTo(3);
    }

    @Test
    void deleteIngredient_Ingredient_Doesnt_Exist_Should_Not_Delete(){
        //Arrange
        Ingredient onion = new Ingredient();
        onion.setType(IngredientType.PIZZA_TOPPING);
        onion.setPrice(3.14f);
        onion.setName("Onion");

        Ingredient tomato = new Ingredient();
        tomato.setType(IngredientType.PIZZA_BASESAUCE);
        tomato.setPrice(2.1f);
        tomato.setName("Tomato");

        Ingredient cheese = new Ingredient();
        cheese.setType(IngredientType.PIZZA_TOPPING);
        cheese.setPrice(4.5f);
        cheese.setName("Cheese");

        database.ingredients.add(onion);
        database.ingredients.add(tomato);
        database.ingredients.add(cheese);

        //Act
        ingredientDao.deleteIngredient("Potato");

        //Assert
        assertThat(database.ingredients.contains(onion)).isEqualTo(true);
        assertThat(database.ingredients.contains(tomato)).isEqualTo(true);
        assertThat(database.ingredients.contains(cheese)).isEqualTo(true);
        assertThat(database.ingredients.size()).isEqualTo(3);
    }

}
