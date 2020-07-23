package com.github.holiver98.repository;

import com.github.holiver98.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByPizzaId(long pizzaId);
    List<Rating> findByUserEmailAddress(String userEmailAddress);
    Rating findByUserEmailAddressAndPizzaId(String userEmailAddress, long pizzaId);
}
