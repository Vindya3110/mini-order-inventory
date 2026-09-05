package com.vindya.mini_order_inventory.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Payload for placing a new order")
public class OrderRequestDTO {

    @NotNull
    @Schema(description = "Identifier of the customer placing the order", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long customerId;

    @Valid
    @NotEmpty
    @Schema(description = "Non-empty list of items to order", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<OrderItemRequestDTO> items;
}