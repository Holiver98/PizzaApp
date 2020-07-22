package com.github.holiver98.repository;

import com.github.holiver98.model.Rating;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.repository.config.BootstrapMode;

@DataJpaTest
public class RatingRepositoryTest {

    @Autowired
    IRatingRepository repo;

    @Test
    void test1(){
        Rating rating = new Rating();
        rating.setPizzaId(4);
        rating.setRating(2);
        rating.setUserEmailAddress("dsadas@sda.hu");
        Rating r = repo.save(rating);
        System.out.println(r);
    }
}
