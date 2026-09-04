package com.vindya.mini_order_inventory.service;

import java.util.List;
import com.vindya.mini_order_inventory.dto.ProductRequestDTO;
import com.vindya.mini_order_inventory.dto.ProductResponseDTO;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO request);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(Long id);

    List<ProductResponseDTO> searchProducts(String name);

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO request);

    void deactivateProduct(Long id);
} 
