package com.github.holiver98.repository;

import com.github.holiver98.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPizzaRepository extends JpaRepository<Pizza, Long> {
}
