package com.github.holiver98.repository;

import com.github.holiver98.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IIngredientRepository extends JpaRepository<Ingredient, String> {
}
