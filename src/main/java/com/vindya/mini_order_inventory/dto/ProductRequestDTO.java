package com.vindya.mini_order_inventory.dto;


import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Payload for creating or updating a product")
public class ProductRequestDTO{
    @NotBlank
    @Schema(description = "Product name", example = "Laptop", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @NotBlank
    @Schema(description = "Product category", example = "Electronics", requiredMode = Schema.RequiredMode.REQUIRED)
    private String category;
    @Positive
    @Schema(description = "Unit price, must be greater than zero", example = "50000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal price;
    @PositiveOrZero
    @Schema(description = "Quantity available in stock", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer availableQuantity;
}