package com.github.holiver98.repository;

import com.github.holiver98.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRatingRepository extends JpaRepository<Rating, Long> {
}
