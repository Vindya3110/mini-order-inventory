package com.vindya.mini_order_inventory.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.vindya.mini_order_inventory.entity.OrderStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Order details returned by the API")
public class OrderResponseDTO {

    @Schema(description = "Unique order identifier", example = "1")
    private Long id;
    @Schema(description = "Identifier of the customer who placed the order", example = "1")
    private Long customerId;
    @Schema(description = "Name of the customer who placed the order", example = "Alice")
    private String customerName;

    @Schema(description = "Total amount for the order", example = "100000.00")
    private BigDecimal totalAmount;
    @Schema(description = "Timestamp when the order was placed", example = "2026-09-05T10:15:30")
    private LocalDateTime orderDate;

    @Schema(description = "Line items included in the order")
    private List<OrderItemResponseDTO> items;
    @Schema(description = "Current order status", example = "ACTIVE")
    private OrderStatus status;
}