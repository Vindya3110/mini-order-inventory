package com.vindya.mini_order_inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A single line item within an order request")
public class OrderItemRequestDTO {

    @NotNull
    @Schema(description = "Identifier of the product being ordered", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long productId;

    @Min(1)
    @Schema(description = "Quantity to order, must be at least 1", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantity;
}