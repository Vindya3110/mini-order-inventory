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

import com.vindya.mini_order_inventory.dto.CustomerRequestDTO;
import com.vindya.mini_order_inventory.dto.CustomerResponseDTO;
import com.vindya.mini_order_inventory.dto.OrderResponseDTO;
import com.vindya.mini_order_inventory.exception.ErrorResponse;
import com.vindya.mini_order_inventory.service.CustomerService;
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
@RequestMapping("/customer")
@RequiredArgsConstructor
@Tag(name = "Customers", description = "Create, retrieve and update customers, and view their orders")
public class CustomerController {
    private final CustomerService customerService;
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Create a customer",
            description = "Registers a new customer. Email must be unique.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Customer created",
                    content = @Content(schema = @Schema(implementation = CustomerResponseDTO.class),
                            examples = @ExampleObject(name = "Created customer", value = """
                                    {
                                      "id": 1,
                                      "name": "Alice",
                                      "email": "alice@gmail.com",
                                      "phone": "9999999999"
                                    }"""))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CustomerResponseDTO> createCustumer(@Valid @RequestBody CustomerRequestDTO requestDTO){
        CustomerResponseDTO customerResponseDTO= customerService.createCustomer(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponseDTO);
    }

    @GetMapping
    @Operation(summary = "List all customers")
    @ApiResponse(responseCode = "200", description = "List of customers")
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers(){
        List<CustomerResponseDTO> customerResponseDTOs=customerService.getAllCustomers();
        return ResponseEntity.ok(customerResponseDTOs);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a customer by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer found",
                    content = @Content(schema = @Schema(implementation = CustomerResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CustomerResponseDTO> getCustomerById(
            @Parameter(description = "Customer id", example = "1") @PathVariable  Long id){
        CustomerResponseDTO response = customerService.getCustomerById(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a customer",
            description = "Updates an existing customer. Email must remain unique across customers.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer updated",
                    content = @Content(schema = @Schema(implementation = CustomerResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
            @Parameter(description = "Customer id", example = "1") @PathVariable  Long id,
            @Valid @RequestBody CustomerRequestDTO request){
        CustomerResponseDTO response = customerService.updateCustomer(id,request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}/orders")
    @Operation(summary = "List a customer's orders",
            description = "Returns all orders placed by the given customer.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders for the customer"),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<OrderResponseDTO>> getCustomerOrders(
            @Parameter(description = "Customer id", example = "1") @PathVariable Long id) {

        return ResponseEntity.ok(orderService.getOrdersByCustomer(id));
    }

}
