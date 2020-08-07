package com.github.holiver98.dal.inmemory;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.IngredientType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class InMemoryIngredientDaoTest{

    private InMemoryIngredientDao ingredientDao;

    @BeforeEach
    void init(){
        ingredientDao = new InMemoryIngredientDao();
    }

    @Test
    void saveIngredient_Passing_Null_Should_Throw_Exception(){
        //Arrange

        //Act
        //Assert
        Assertions.assertThrows(NullPointerException.class,
                () -> ingredientDao.saveIngredient(null));
        assertThat(ingredientDao.getIngredients().size()).isEqualTo(0);
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
        assertThat(ingredientDao.getIngredients().size()).isEqualTo(1);
        assertThat(ingredientDao.getIngredients().contains(ingredient)).isTrue();
    }

    @Test
    void saveIngredient_Ingredient_Already_Exists_Should_Not_Save(){
        //Arrange
        Ingredient ingredient = new Ingredient();
        ingredient.setType(IngredientType.PIZZA_TOPPING);
        ingredient.setPrice(3.14f);
        ingredient.setName("Onion");

        ingredientDao.getIngredients().add(ingredient);

        //Act
        ingredientDao.saveIngredient(ingredient);

        //Assert
        assertThat(ingredientDao.getIngredients().size()).isEqualTo(1);
        assertThat(ingredientDao.getIngredients().contains(ingredient)).isTrue();
    }

    @Test
    void getIngredientByName_Passing_Null_Should_Throw_Exception(){
        //Arrange
        Ingredient ingredient = new Ingredient();
        ingredient.setType(IngredientType.PIZZA_TOPPING);
        ingredient.setPrice(3.14f);
        ingredient.setName("Onion");

        ingredientDao.getIngredients().add(ingredient);

        //Act
        //Assert
        Assertions.assertThrows(NullPointerException.class,
                () -> ingredientDao.getIngredientByName(null));
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

        ingredientDao.getIngredients().add(onion);
        ingredientDao.getIngredients().add(tomato);
        ingredientDao.getIngredients().add(cheese);

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

        ingredientDao.getIngredients().add(onion);
        ingredientDao.getIngredients().add(tomato);
        ingredientDao.getIngredients().add(cheese);

        //Act
        List<Ingredient> result = ingredientDao.getIngredientsOfType(IngredientType.PIZZA_TOPPING);

        //Assert
        assertThat(result.contains(onion)).isTrue();
        assertThat(result.contains(cheese)).isTrue();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void getIngredientsOfType_Passing_Null_Should_Throw_Exception(){
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

        ingredientDao.getIngredients().add(onion);
        ingredientDao.getIngredients().add(tomato);
        ingredientDao.getIngredients().add(cheese);

        //Act
        //Assert
        Assertions.assertThrows(NullPointerException.class,
                () -> ingredientDao.getIngredientsOfType(null));
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

        ingredientDao.getIngredients().add(onion);
        ingredientDao.getIngredients().add(cheese);

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

        ingredientDao.getIngredients().add(cheese);

        //Act
        ingredientDao.updateIngredient(onion);

        //Assert
        assertThat(ingredientDao.getIngredients().size()).isEqualTo(1);
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

        ingredientDao.getIngredients().add(onion);
        ingredientDao.getIngredients().add(cheese);

        Ingredient newOnion = new Ingredient();
        newOnion.setType(IngredientType.PIZZA_TOPPING);
        newOnion.setPrice(4.14f);
        newOnion.setName("Onion");

        //Act
        ingredientDao.updateIngredient(newOnion);

        //Assert
        assertThat(ingredientDao.getIngredients().size()).isEqualTo(2);
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

        ingredientDao.getIngredients().add(onion);
        ingredientDao.getIngredients().add(cheese);

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
    void updateIngredient_Passing_Null_Should_Throw_Exception(){
        //Arrange
        Ingredient onion = new Ingredient();
        onion.setType(IngredientType.PIZZA_TOPPING);
        onion.setPrice(3.14f);
        onion.setName("Onion");

        Ingredient initialOnion = new Ingredient();
        initialOnion.setType(onion.getType());
        initialOnion.setPrice(onion.getPrice());
        initialOnion.setName(onion.getName());

        ingredientDao.getIngredients().add(onion);

        //Act
        //Assert
        Assertions.assertThrows(NullPointerException.class,
                () -> ingredientDao.updateIngredient(null));
        assertThat(ingredientDao.getIngredients().size()).isEqualTo(1);
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

        ingredientDao.getIngredients().add(onion);
        ingredientDao.getIngredients().add(tomato);
        ingredientDao.getIngredients().add(cheese);

        //Act
        ingredientDao.deleteIngredient("Tomato");

        //Assert
        assertThat(ingredientDao.getIngredients().contains(onion)).isTrue();
        assertThat(ingredientDao.getIngredients().contains(tomato)).isEqualTo(false);
        assertThat(ingredientDao.getIngredients().contains(cheese)).isTrue();
        assertThat(ingredientDao.getIngredients().size()).isEqualTo(2);
    }

    @Test
    void deleteIngredient_Passing_Null_Should_Throw_Exception(){
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

        ingredientDao.getIngredients().add(onion);
        ingredientDao.getIngredients().add(tomato);
        ingredientDao.getIngredients().add(cheese);

        //Act
        //Assert
        Assertions.assertThrows(NullPointerException.class,
                () -> ingredientDao.deleteIngredient(null));
        assertThat(ingredientDao.getIngredients().contains(onion)).isTrue();
        assertThat(ingredientDao.getIngredients().contains(tomato)).isTrue();
        assertThat(ingredientDao.getIngredients().contains(cheese)).isTrue();
        assertThat(ingredientDao.getIngredients().size()).isEqualTo(3);
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

        ingredientDao.getIngredients().add(onion);
        ingredientDao.getIngredients().add(tomato);
        ingredientDao.getIngredients().add(cheese);

        //Act
        ingredientDao.deleteIngredient("Potato");

        //Assert
        assertThat(ingredientDao.getIngredients().contains(onion)).isTrue();
        assertThat(ingredientDao.getIngredients().contains(tomato)).isTrue();
        assertThat(ingredientDao.getIngredients().contains(cheese)).isTrue();
        assertThat(ingredientDao.getIngredients().size()).isEqualTo(3);
    }

}
