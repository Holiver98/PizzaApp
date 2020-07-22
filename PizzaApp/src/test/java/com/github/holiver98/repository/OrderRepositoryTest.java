package com.github.holiver98.repository;

import com.github.holiver98.model.Ingredient;
import com.github.holiver98.model.IngredientType;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.repository.config.BootstrapMode;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    IIngredientRepository repo;

    @Test
    void test1(){
        Ingredient in = new Ingredient();
        in.setName("Tomato");
        in.setPrice(23f);
        in.setType(IngredientType.PIZZA_BASESAUCE);

        Ingredient si = repo.save(in);
        System.out.println(si);
    }
}
