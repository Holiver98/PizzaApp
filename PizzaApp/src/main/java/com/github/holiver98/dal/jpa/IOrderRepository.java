package com.github.holiver98.dal.jpa;

import com.github.holiver98.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long>{
}
