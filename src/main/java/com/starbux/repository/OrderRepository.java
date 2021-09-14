package com.starbux.repository;

import com.starbux.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findById(Integer id);

    @Query(value = "SELECT coalesce(sum(price - discount), 0) FROM Order WHERE CUSTOMER = :username")
    Double totalByCustomer(@Param("username") String userName);
}
