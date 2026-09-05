package com.vindya.mini_order_inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {

    @NotNull
    private Long productId;

    @Min(1)
    private Integer quantity;
}