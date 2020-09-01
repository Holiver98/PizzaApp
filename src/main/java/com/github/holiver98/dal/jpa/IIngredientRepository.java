package com.github.holiver98.dal.jpa;

import com.github.holiver98.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IIngredientRepository extends JpaRepository<Ingredient, String> {
    @Query(value = "select count(ingredient_name) from pizzas_ingredients where ingredient_name = :name", nativeQuery = true)
    int getNumberOfReferencesByPizzasOnThisIngredient(@Param("name") String ingredientName);
}
