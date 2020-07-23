package com.github.holiver98.dal.jpa;

import com.github.holiver98.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPizzaRepository extends JpaRepository<Pizza, Long> {
}
