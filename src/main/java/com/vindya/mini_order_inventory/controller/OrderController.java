package com.vindya.mini_order_inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vindya.mini_order_inventory.dto.OrderRequestDTO;
import com.vindya.mini_order_inventory.dto.OrderResponseDTO;
import com.vindya.mini_order_inventory.exception.ErrorResponse;
import com.vindya.mini_order_inventory.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping ("/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Place, retrieve and cancel orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Place an order",
            description = """
                    Creates an order for a customer. For each item the product must exist, be active,
                    and have enough stock. Stock is decremented and the order total is computed from the
                    product prices captured at order time.""")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created",
                    content = @Content(schema = @Schema(implementation = OrderResponseDTO.class),
                            examples = @ExampleObject(name = "Created order", value = """
                                    {
                                      "id": 1,
                                      "customerId": 1,
                                      "customerName": "Alice",
                                      "totalAmount": 100000.00,
                                      "orderDate": "2026-09-05T10:15:30",
                                      "items": [
                                        {
                                          "productId": 1,
                                          "productName": "Laptop",
                                          "quantity": 2,
                                          "unitPrice": 50000.00
                                        }
                                      ],
                                      "status": "ACTIVE"
                                    }"""))),
            @ApiResponse(responseCode = "400", description = "Validation error, inactive product or insufficient stock",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Customer or product not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OrderResponseDTO> createOrder(
            @Valid @RequestBody OrderRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an order by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order found",
                    content = @Content(schema = @Schema(implementation = OrderResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @Parameter(description = "Order id", example = "1") @PathVariable Long id) {

        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "List orders for a customer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders for the customer"),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByCustomer(
            @Parameter(description = "Customer id", example = "1") @PathVariable Long customerId) {

        return ResponseEntity.ok(
            orderService.getOrdersByCustomer(customerId)
        );
    }

    @GetMapping
    @Operation(summary = "List all orders")
    @ApiResponse(responseCode = "200", description = "List of all orders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel an order",
            description = """
                    Cancels an ACTIVE order and restores the reserved stock back to inventory.
                    Completed or already-cancelled orders cannot be cancelled.""")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order cancelled",
                    content = @Content(schema = @Schema(implementation = OrderResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Order is completed or already cancelled",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OrderResponseDTO> cancelOrder(
            @Parameter(description = "Order id", example = "1") @PathVariable Long id) {

        return ResponseEntity.ok(orderService.cancelOrder(id));
    }
}
