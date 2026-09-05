package com.vindya.mini_order_inventory.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    @NotNull
    private Long customerId;

    @Valid
    @NotEmpty
    private List<OrderItemRequestDTO> items;
}