package com.github.holiver98.dal.jpa;

import com.github.holiver98.model.Rating;
import com.github.holiver98.model.RatingJpaKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRatingRepository extends JpaRepository<Rating, RatingJpaKey> {
    List<Rating> findByPizzaId(long pizzaId);
    List<Rating> findByUserEmailAddress(String userEmailAddress);
    Rating findByUserEmailAddressAndPizzaId(String userEmailAddress, long pizzaId);
}
