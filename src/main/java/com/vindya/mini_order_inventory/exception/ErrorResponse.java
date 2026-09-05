package com.vindya.mini_order_inventory.exception;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Standard error payload returned for all failed requests")
public class ErrorResponse {

    @Schema(description = "Time the error occurred", example = "2026-09-05T10:15:30")
    private LocalDateTime timestamp;
    @Schema(description = "HTTP status code", example = "404")
    private int status;
    @Schema(description = "Short error label", example = "Not Found")
    private String error;
    @Schema(description = "Human-readable error detail", example = "Product not found")
    private String message;
}