package com.vindya.mini_order_inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import com.vindya.mini_order_inventory.dto.ProductRequestDTO;
import com.vindya.mini_order_inventory.dto.ProductResponseDTO;
import com.vindya.mini_order_inventory.entity.Customer;
import com.vindya.mini_order_inventory.service.CustomerService;
import com.vindya.mini_order_inventory.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/customer")
@RequiredArgsConstructor 
public class CustomerController {
    private final CustomerService customerService;
    private final OrderService orderService;

    @PostMapping 
    public ResponseEntity<CustomerResponseDTO> createCustumer(@Valid @RequestBody CustomerRequestDTO requestDTO){
        CustomerResponseDTO customerResponseDTO= customerService.createCustomer(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponseDTO);
    }

    @GetMapping 
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers(){
        List<CustomerResponseDTO> customerResponseDTOs=customerService.getAllCustomers();
        return ResponseEntity.ok(customerResponseDTOs);
    }
    
    @GetMapping("/{id}") 
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable  Long id){
        CustomerResponseDTO response = customerService.getCustomerById(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable  Long id,@Valid @RequestBody CustomerRequestDTO request){
        CustomerResponseDTO response = customerService.updateCustomer(id,request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderResponseDTO>> getCustomerOrders(
            @PathVariable Long id) {

        return ResponseEntity.ok(orderService.getOrdersByCustomer(id));
    }

}
