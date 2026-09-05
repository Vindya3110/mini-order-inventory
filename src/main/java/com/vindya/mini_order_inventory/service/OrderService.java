package com.vindya.mini_order_inventory.service;

import java.util.List;

import com.vindya.mini_order_inventory.dto.OrderRequestDTO;
import com.vindya.mini_order_inventory.dto.OrderResponseDTO;

public interface OrderService {

    OrderResponseDTO createOrder(OrderRequestDTO request); 
    OrderResponseDTO getOrderById(Long id);
    List<OrderResponseDTO> getOrdersByCustomer(Long customerId);
    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO cancelOrder(Long id);

} 
