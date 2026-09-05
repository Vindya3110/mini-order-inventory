package com.vindya.mini_order_inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Payload for creating or updating a customer")
public class CustomerRequestDTO {

    @NotBlank
    @Schema(description = "Customer full name", example = "Alice", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Email
    @NotBlank
    @Schema(description = "Customer email address (must be unique)", example = "alice@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank
    @Schema(description = "Customer phone number", example = "9999999999", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;
}