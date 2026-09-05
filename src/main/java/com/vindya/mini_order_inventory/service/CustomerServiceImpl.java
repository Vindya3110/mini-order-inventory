package com.vindya.mini_order_inventory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vindya.mini_order_inventory.dto.CustomerRequestDTO;
import com.vindya.mini_order_inventory.dto.CustomerResponseDTO;
import com.vindya.mini_order_inventory.dto.ProductRequestDTO;
import com.vindya.mini_order_inventory.dto.ProductResponseDTO;
import com.vindya.mini_order_inventory.entity.Customer;
import com.vindya.mini_order_inventory.entity.Product;
import com.vindya.mini_order_inventory.exception.DuplicateResourceException;
import com.vindya.mini_order_inventory.exception.ResourceNotFoundException;
import com.vindya.mini_order_inventory.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    private CustomerResponseDTO mapToResponse(Customer customer){
        CustomerResponseDTO response = new CustomerResponseDTO();
    response.setId(customer.getId());
    response.setName(customer.getName());
    response.setEmail(customer.getEmail());
    response.setPhone(customer.getPhone());
    return response;
    }


    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO request) {
         customerRepository.findByEmail(request.getEmail())
            .ifPresent(customer -> {
                throw new DuplicateResourceException("Email already exists");
            });

        Customer customer=new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());

        Customer newcCustomer=customerRepository.save(customer);

        return this.mapToResponse(newcCustomer);
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        List<Customer> customers=customerRepository.findAll();
        List<CustomerResponseDTO> customerResponseDTOs=new ArrayList<>();
        for(Customer customer:customers){
            customerResponseDTOs.add(this.mapToResponse(customer));
        }
        return customerResponseDTOs;
    }

    @Override
    public CustomerResponseDTO getCustomerById(Long id) {
        Customer customer=customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("no such customer"));
        return mapToResponse(customer);
    }

    @Override
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO request) {
        Customer customer = customerRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

    Optional<Customer> existingCustomer =
        customerRepository.findByEmail(request.getEmail());

    if (existingCustomer.isPresent() &&
    !existingCustomer.get().getId().equals(id)) {
        throw new DuplicateResourceException("Email already exists");
        }    
    
    customer.setName(request.getName());
    customer.setEmail(request.getEmail());
    customer.setPhone(request.getPhone());

    Customer updatedCustomer = customerRepository.save(customer);

    return mapToResponse(updatedCustomer);
        
    }
    
}
