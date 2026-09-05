package com.vindya.mini_order_inventory.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vindya.mini_order_inventory.dto.*;
import com.vindya.mini_order_inventory.entity.*;
import com.vindya.mini_order_inventory.exception.*;
import com.vindya.mini_order_inventory.repository.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderItemRepository orderItemRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    void shouldCreateOrderSuccessfully() {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Alice");

        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(BigDecimal.valueOf(50000));
        product.setAvailableQuantity(10);
        product.setActive(true);

        OrderItemRequestDTO item =
                new OrderItemRequestDTO(1L, 2);

        OrderRequestDTO request =
                new OrderRequestDTO(1L, List.of(item));

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(i -> i.getArgument(0));

        OrderResponseDTO response =
                orderService.createOrder(request);

        assertEquals("Alice", response.getCustomerName());
        assertEquals(BigDecimal.valueOf(100000),
                response.getTotalAmount());

        assertEquals(8, product.getAvailableQuantity());

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void shouldThrowWhenStockIsInsufficient() {

        Customer customer = new Customer();
        customer.setId(1L);

        Product product = new Product();
        product.setId(1L);
        product.setPrice(BigDecimal.valueOf(50000));
        product.setAvailableQuantity(1);
        product.setActive(true);

        OrderRequestDTO request = new OrderRequestDTO(
                1L,
                List.of(new OrderItemRequestDTO(1L, 5))
        );

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(
                InvalidOperationException.class,
                () -> orderService.createOrder(request)
        );

        verify(orderRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenCustomerDoesNotExist() {

        when(customerRepository.findById(99L))
                .thenReturn(Optional.empty());

        OrderRequestDTO request =
                new OrderRequestDTO(99L, List.of());

        assertThrows(
                ResourceNotFoundException.class,
                () -> orderService.createOrder(request)
        );
    }

    @Test
    void shouldCancelOrderAndRestoreInventory() {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Alice");

        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setAvailableQuantity(5);
        product.setPrice(BigDecimal.valueOf(50000));

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(3);
        item.setUnitPrice(BigDecimal.valueOf(50000));

        Order order = new Order();
        order.setCustomer(customer);          // ⭐ Missing line
        order.setStatus(OrderStatus.ACTIVE);
        order.setItems(List.of(item));

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(i -> i.getArgument(0));

        OrderResponseDTO response = orderService.cancelOrder(1L);

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
        assertEquals(8, product.getAvailableQuantity());
        assertEquals("Alice", response.getCustomerName());
    }
    @Test
    void shouldNotCancelCompletedOrder() {

        Order order = new Order();
        order.setStatus(OrderStatus.COMPLETED);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        assertThrows(
                InvalidOperationException.class,
                () -> orderService.cancelOrder(1L)
        );

        verify(orderRepository, never()).save(any());
    }

}