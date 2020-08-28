package com.github.holiver98.dal.jpa;

import com.github.holiver98.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IPizzaRepository extends JpaRepository<Pizza, Long> {
    List<Pizza> findByIsCustom(boolean isCustom);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query(value = "delete from ratings where pizza_id = :id", nativeQuery = true)
    void deleteRatingsOfPizza(@Param("id") Long pizzaId);

}
