package com.vindya.mini_order_inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Customer details returned by the API")
public class CustomerResponseDTO {

    @Schema(description = "Unique customer identifier", example = "1")
    private Long id;
    @Schema(description = "Customer full name", example = "Alice")
    private String name;
    @Schema(description = "Customer email address", example = "alice@gmail.com")
    private String email;
    @Schema(description = "Customer phone number", example = "9999999999")
    private String phone;
}