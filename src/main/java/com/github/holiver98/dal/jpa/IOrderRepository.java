package com.github.holiver98.dal.jpa;

import com.github.holiver98.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long>{
    @Query(value = "select id, email, order_date, total_price from orders\n" +
            "left join orders_pizzas\n" +
            "on orders.id = orders_pizzas.order_id\n" +
            "where :id = pizza_id", nativeQuery = true)
    List<Order> findByPizzaId(@Param("id") long pizzaId);
}
