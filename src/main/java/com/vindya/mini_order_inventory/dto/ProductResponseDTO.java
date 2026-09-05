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
@Schema(description = "Product details returned by the API")
public class ProductResponseDTO{
    @Schema(description = "Unique product identifier", example = "1")
    private Long id;
    @Schema(description = "Product name", example = "Laptop")
    private String name;
    @Schema(description = "Product category", example = "Electronics")
    private String category;
    @Schema(description = "Unit price", example = "50000.00")
    private BigDecimal price;
    @Schema(description = "Quantity currently available in stock", example = "10")
    private Integer availableQuantity;
    @Schema(description = "Whether the product is active (false once deactivated)", example = "true")
    private Boolean active=true;
}