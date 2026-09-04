package com.vindya.mini_order_inventory.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vindya.mini_order_inventory.dto.ProductRequestDTO;
import com.vindya.mini_order_inventory.dto.ProductResponseDTO;
import com.vindya.mini_order_inventory.entity.Product;
import com.vindya.mini_order_inventory.exception.ResourceNotFoundException;
import com.vindya.mini_order_inventory.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;

    private ProductResponseDTO mapToResponse(Product product){
        ProductResponseDTO response = new ProductResponseDTO();
    response.setId(product.getId());
    response.setName(product.getName());
    response.setCategory(product.getCategory());
    response.setPrice(product.getPrice());
    response.setAvailableQuantity(product.getAvailableQuantity());
    response.setActive(product.getActive());

    return response;
    }


    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO request) {
    Product product = new Product();
    product.setName(request.getName());
    product.setCategory(request.getCategory());
    product.setPrice(request.getPrice());
    product.setAvailableQuantity(request.getAvailableQuantity());

    Product savedProduct = productRepository.save(product);

    ProductResponseDTO response = this.mapToResponse(savedProduct);
    return response;
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> productList=productRepository.findAll();
        List<ProductResponseDTO> productResponseDTOs=new ArrayList<>();
        for(Product product : productList){
            productResponseDTOs.add(mapToResponse(product));
        }
        return productResponseDTOs;
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product=productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        ProductResponseDTO response = this.mapToResponse(product);

    return response;
    }

    @Override
    public List<ProductResponseDTO> searchProducts(String name) {
        List<Product> productList=productRepository.findByNameContainingIgnoreCase(name);
        List<ProductResponseDTO> productResponseDTOs=new ArrayList<>();
        for(Product product : productList){
            productResponseDTOs.add(mapToResponse(product));
}
        return productResponseDTOs;
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO request) {
        Product product=productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
       product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setAvailableQuantity(request.getAvailableQuantity());

        Product updated = productRepository.save(product);
        return mapToResponse(updated);
    }

    @Override
    public void deactivateProduct(Long id) {
        Product product=productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setActive(false);
    }
}
