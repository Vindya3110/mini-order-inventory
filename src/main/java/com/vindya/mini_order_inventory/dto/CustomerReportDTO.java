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
public class CustomerReportDTO {

    private String customerName;
    private Long numberOfOrders;
    private BigDecimal totalAmountSpent;
    private BigDecimal averageOrderValue;
}
