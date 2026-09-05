package com.vindya.mini_order_inventory.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A single line item within an order response")
public class OrderItemResponseDTO {

    @Schema(description = "Identifier of the ordered product", example = "1")
    private Long productId;
    @Schema(description = "Name of the ordered product", example = "Laptop")
    private String productName;
    @Schema(description = "Quantity ordered", example = "2")
    private Integer quantity;
    @Schema(description = "Unit price captured at the time of ordering", example = "50000.00")
    private BigDecimal unitPrice;
}