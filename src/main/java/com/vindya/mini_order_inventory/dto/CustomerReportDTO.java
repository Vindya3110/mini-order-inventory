package com.vindya.mini_order_inventory.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Aggregated spending report for a single customer (active orders only)")
public class CustomerReportDTO {

    @Schema(description = "Customer full name", example = "Alice")
    private String customerName;
    @Schema(description = "Number of active orders placed by the customer", example = "3")
    private Long numberOfOrders;
    @Schema(description = "Total amount spent across active orders", example = "150000.00")
    private BigDecimal totalAmountSpent;
    @Schema(description = "Average value per active order", example = "50000.00")
    private BigDecimal averageOrderValue;
}
