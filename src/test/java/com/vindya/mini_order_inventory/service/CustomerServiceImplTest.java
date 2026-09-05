package com.vindya.mini_order_inventory.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vindya.mini_order_inventory.dto.CustomerRequestDTO;
import com.vindya.mini_order_inventory.entity.Customer;
import com.vindya.mini_order_inventory.exception.DuplicateResourceException;
import com.vindya.mini_order_inventory.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void shouldThrowWhenEmailAlreadyExists() {

        Customer existing = new Customer();
        existing.setEmail("alice@gmail.com");

        CustomerRequestDTO request = new CustomerRequestDTO(
                "Bob",
                "alice@gmail.com",
                "9999999999"
        );

        when(customerRepository.findByEmail("alice@gmail.com"))
                .thenReturn(Optional.of(existing));

        assertThrows(
                DuplicateResourceException.class,
                () -> customerService.createCustomer(request)
        );

        verify(customerRepository, never()).save(any());
    }
}