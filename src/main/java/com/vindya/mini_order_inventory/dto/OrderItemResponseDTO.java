package com.vindya.mini_order_inventory.dto;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {

    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
}