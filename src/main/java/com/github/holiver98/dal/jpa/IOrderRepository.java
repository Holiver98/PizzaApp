package com.github.holiver98.dal.jpa;

import com.github.holiver98.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long>{
    @Query(value = "select * from orders where :id in (select pizza_id from orders_pizzas)", nativeQuery = true)
    List<Order> findByPizzaId(@Param("id") long pizzaId);
}
