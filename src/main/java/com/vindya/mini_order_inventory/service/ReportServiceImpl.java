package com.vindya.mini_order_inventory.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vindya.mini_order_inventory.dto.CustomerReportDTO;
import com.vindya.mini_order_inventory.dto.ProductReportDTO;
import com.vindya.mini_order_inventory.entity.Customer;
import com.vindya.mini_order_inventory.exception.ResourceNotFoundException;
import com.vindya.mini_order_inventory.repository.CustomerRepository;
import com.vindya.mini_order_inventory.repository.OrderItemRepository;
import com.vindya.mini_order_inventory.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public CustomerReportDTO getCustomerReport(Long customerId) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        List<Object[]> result = orderRepository.getCustomerStatistics(customerId);

        Object[] stats = result.get(0);

        Long orderCount = (Long) stats[0];
        BigDecimal totalSpent = (BigDecimal) stats[1];
        Double avg = (Double) stats[2];

        return new CustomerReportDTO(
                customer.getName(),
                orderCount,
                totalSpent,
                BigDecimal.valueOf(avg)
        );
    }

    @Override
    public List<ProductReportDTO> getProductReport() {

        List<Object[]> rows =orderItemRepository.getProductSalesReport();

        List<ProductReportDTO> report = new ArrayList<>();

        for(Object[] row : rows){
            report.add(new ProductReportDTO(
                    (Long) row[0],
                    (String) row[1],
                    (Long) row[2],
                    (BigDecimal) row[3]
            ));
        }

        return report;
    }

    @Override
    public List<ProductReportDTO> getTopProducts(Integer limit) {

        Pageable pageable = PageRequest.of(0, limit);

        List<Object[]> rows =orderItemRepository.getTopProducts(pageable);

        List<ProductReportDTO> report = new ArrayList<>();

        for(Object[] row : rows){
            report.add(new ProductReportDTO(
                    (Long) row[0],
                    (String) row[1],
                    (Long) row[2],
                    (BigDecimal) row[3]
            ));
        }

        return report;
    }


}