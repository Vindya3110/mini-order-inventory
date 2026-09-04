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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vindya.mini_order_inventory.dto.ProductRequestDTO;
import com.vindya.mini_order_inventory.dto.ProductResponseDTO;
import com.vindya.mini_order_inventory.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/products")
@RequiredArgsConstructor 
public class ProductController {

    private  final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO request){
            ProductResponseDTO response = productService.createProduct(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping 
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        List<ProductResponseDTO> productResponseDTOs=productService.getAllProducts();
        return ResponseEntity.ok(productResponseDTOs);
    }

    @GetMapping("/{id}") 
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable  Long id){
        ProductResponseDTO response = productService.getProductById(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProduct(@RequestParam String name ){
        List<ProductResponseDTO> productResponseDTOs=productService.searchProducts(name);
        return ResponseEntity.ok(productResponseDTOs); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable  Long id,@Valid @RequestBody ProductRequestDTO request){
        ProductResponseDTO response = productService.updateProduct(id,request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    
}
