package com.vindya.mini_order_inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vindya.mini_order_inventory.dto.ProductRequestDTO;
import com.vindya.mini_order_inventory.dto.ProductResponseDTO;
import com.vindya.mini_order_inventory.exception.ErrorResponse;
import com.vindya.mini_order_inventory.service.ProductService;

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
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Create, search, update and deactivate products")
public class ProductController {

    private  final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a product",
            description = "Registers a new product in the catalogue with initial stock.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created",
                    content = @Content(schema = @Schema(implementation = ProductResponseDTO.class),
                            examples = @ExampleObject(name = "Created product", value = """
                                    {
                                      "id": 1,
                                      "name": "Laptop",
                                      "category": "Electronics",
                                      "price": 50000.00,
                                      "availableQuantity": 10,
                                      "active": true
                                    }"""))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO request){
            ProductResponseDTO response = productService.createProduct(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "List all products", description = "Returns every product in the catalogue.")
    @ApiResponse(responseCode = "200", description = "List of products")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        List<ProductResponseDTO> productResponseDTOs=productService.getAllProducts();
        return ResponseEntity.ok(productResponseDTOs);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ProductResponseDTO> getProductById(
            @Parameter(description = "Product id", example = "1") @PathVariable  Long id){
        ProductResponseDTO response = productService.getProductById(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by name",
            description = "Case-insensitive search matching any part of the product name.")
    @ApiResponse(responseCode = "200", description = "Matching products")
    public ResponseEntity<List<ProductResponseDTO>> searchProduct(
            @Parameter(description = "Text to match within the product name", example = "lap")
            @RequestParam String name ){
        List<ProductResponseDTO> productResponseDTOs=productService.searchProducts(name);
        return ResponseEntity.ok(productResponseDTOs);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Replaces the mutable fields of an existing product.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated",
                    content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @Parameter(description = "Product id", example = "1") @PathVariable  Long id,
            @Valid @RequestBody ProductRequestDTO request){
        ProductResponseDTO response = productService.updateProduct(id,request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate a product",
            description = "Soft-deletes a product. Fails if the product is already referenced by an order.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deactivated",
                    content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Product has existing orders and cannot be deactivated",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ProductResponseDTO> deactivateProduct(
            @Parameter(description = "Product id", example = "1") @PathVariable Long id) {

        return ResponseEntity.ok(productService.deactivateProduct(id));
    }
}
