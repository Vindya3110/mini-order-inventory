package com.vindya.mini_order_inventory.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor 
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private BigDecimal totalAmount;

    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order",
               cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @Enumerated(EnumType.STRING)
private OrderStatus status;

    @PrePersist
    public void onCreate() {
        orderDate = LocalDateTime.now();
        status = OrderStatus.ACTIVE;
    }
}