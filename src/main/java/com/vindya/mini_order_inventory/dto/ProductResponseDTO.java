package com.vindya.mini_order_inventory.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO{
    private Long id;
    private String name;
    private String category;
    private BigDecimal price;
    private Integer availableQuantity;
    private Boolean active=true;
}