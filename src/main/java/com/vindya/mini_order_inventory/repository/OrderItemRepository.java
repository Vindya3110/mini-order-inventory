package com.vindya.mini_order_inventory.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vindya.mini_order_inventory.entity.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
        boolean existsByProductId(Long productId);

        @Query("""
        SELECT
        oi.product.id,
        oi.product.name,
        SUM(oi.quantity),
        SUM(oi.quantity * oi.unitPrice)
        FROM OrderItem oi
        WHERE oi.order.status='ACTIVE'
        GROUP BY oi.product.id, oi.product.name
        ORDER BY SUM(oi.quantity) DESC
        """)
        List<Object[]> getProductSalesReport();

        @Query("""
        SELECT
        oi.product.id,
        oi.product.name,
        SUM(oi.quantity),
        SUM(oi.quantity * oi.unitPrice)
        FROM OrderItem oi
        WHERE oi.order.status='ACTIVE'
        GROUP BY oi.product.id, oi.product.name
        ORDER BY SUM(oi.quantity) DESC
        """)
        List<Object[]> getTopProducts(Pageable pageable);
}