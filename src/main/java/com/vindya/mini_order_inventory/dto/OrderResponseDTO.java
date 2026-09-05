package com.vindya.mini_order_inventory.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.vindya.mini_order_inventory.entity.OrderStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

    private Long id;
    private Long customerId;
    private String customerName;

    private BigDecimal totalAmount;
    private LocalDateTime orderDate;

    private List<OrderItemResponseDTO> items;
    private OrderStatus status;
}