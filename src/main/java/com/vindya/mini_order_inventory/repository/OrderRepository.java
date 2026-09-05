package com.vindya.mini_order_inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vindya.mini_order_inventory.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);

    @Query("""
        SELECT
        COUNT(o),
        COALESCE(SUM(o.totalAmount),0),
        COALESCE(AVG(o.totalAmount),0)
        FROM Order o
        WHERE o.customer.id = :customerId
        AND o.status = 'ACTIVE'
        """)
    List<Object[]> getCustomerStatistics(Long customerId);
}