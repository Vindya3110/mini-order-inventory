package com.vindya.mini_order_inventory.service;

import java.util.List;

import com.vindya.mini_order_inventory.dto.CustomerRequestDTO;
import com.vindya.mini_order_inventory.dto.CustomerResponseDTO;

public interface CustomerService {

    CustomerResponseDTO createCustomer(CustomerRequestDTO request);

    List<CustomerResponseDTO> getAllCustomers();

    CustomerResponseDTO getCustomerById(Long id);

    CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO request);

}
