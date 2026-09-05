package com.vindya.mini_order_inventory.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vindya.mini_order_inventory.dto.OrderItemRequestDTO;
import com.vindya.mini_order_inventory.dto.OrderItemResponseDTO;
import com.vindya.mini_order_inventory.dto.OrderRequestDTO;
import com.vindya.mini_order_inventory.dto.OrderResponseDTO;
import com.vindya.mini_order_inventory.entity.Customer;
import com.vindya.mini_order_inventory.entity.Order;
import com.vindya.mini_order_inventory.entity.OrderItem;
import com.vindya.mini_order_inventory.entity.OrderStatus;
import com.vindya.mini_order_inventory.entity.Product;
import com.vindya.mini_order_inventory.exception.InvalidOperationException;
import com.vindya.mini_order_inventory.exception.ResourceNotFoundException;
import com.vindya.mini_order_inventory.repository.CustomerRepository;
import com.vindya.mini_order_inventory.repository.OrderRepository;
import com.vindya.mini_order_inventory.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class OrderServiceImpl implements OrderService{
    
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;


    @Override
    @Transactional 
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Order order = new Order();
        order.setCustomer(customer);

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            if (!product.getActive()) {
                throw new InvalidOperationException("Product is inactive");
            }

            if (product.getAvailableQuantity() < itemRequest.getQuantity()) {
            throw new InvalidOperationException("Insufficient stock");
            }

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setUnitPrice(product.getPrice());

            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            total = total.add(lineTotal);

            product.setAvailableQuantity(product.getAvailableQuantity() - itemRequest.getQuantity());

            items.add(item);

        }

        order.setItems(items);
        order.setTotalAmount(total);
        Order savedOrder = orderRepository.save(order);
        return mapToResponse(savedOrder); 
        
    }

    private OrderResponseDTO mapToResponse(Order order) {
        OrderResponseDTO orderResponseDTO=new OrderResponseDTO();
        orderResponseDTO.setId(order.getId());
        orderResponseDTO.setCustomerId(order.getCustomer().getId());
        orderResponseDTO.setCustomerName(order.getCustomer().getName());
        orderResponseDTO.setTotalAmount(order.getTotalAmount());
        orderResponseDTO.setOrderDate(order.getOrderDate());
        orderResponseDTO.setStatus(order.getStatus());
        List<OrderItemResponseDTO> items = new ArrayList<>();
        for(OrderItem orderItem:order.getItems()){
            items.add(
            new OrderItemResponseDTO(
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice()
            )
        );
        }
        orderResponseDTO.setItems(items);
        return orderResponseDTO;

    }


    @Override
    public OrderResponseDTO getOrderById(Long id) {

        Order order = orderRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Order not found"));

        return mapToResponse(order);
}

    @Override
    public List<OrderResponseDTO> getOrdersByCustomer(Long customerId) {
        Customer customer=customerRepository.findById(customerId).orElseThrow(()-> new ResourceNotFoundException("no such customer"));
        List<Order> orders=orderRepository.findByCustomerId(customerId);

        List<OrderResponseDTO> orderResponseDTOs=new ArrayList<>();
        for(Order order:orders){
            orderResponseDTOs.add(mapToResponse(order));
        }
        return orderResponseDTOs;
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {

        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDTO> response = new ArrayList<>();

        for (Order order : orders) {
            response.add(mapToResponse(order));
        }

        return response;
    }

    @Override
    @Transactional
    public OrderResponseDTO cancelOrder(Long id) {

        Order order = orderRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.COMPLETED) {
        throw new InvalidOperationException(
                "Completed orders cannot be cancelled");
        }
        
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new InvalidOperationException("Order already cancelled");
        }

        for (OrderItem item : order.getItems()) {

            Product product = item.getProduct();

            product.setAvailableQuantity(
                    product.getAvailableQuantity() + item.getQuantity());
        }

        order.setStatus(OrderStatus.CANCELLED);

        Order updated = orderRepository.save(order);

        return mapToResponse(updated);
    }


}
