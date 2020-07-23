package com.github.holiver98.dal.jpa;

import com.github.holiver98.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPizzaRepository extends JpaRepository<Pizza, Long> {
    List<Pizza> findByIsCustom(boolean isCustom);
}
