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
public class ProductReportDTO {

    private Long productId;
    private String productName;
    private Long quantitySold;
    private BigDecimal totalRevenue;
}