package com.vindya.mini_order_inventory.service;

import java.util.List;

import com.vindya.mini_order_inventory.dto.CustomerReportDTO;
import com.vindya.mini_order_inventory.dto.ProductReportDTO;

public interface ReportService {

    CustomerReportDTO getCustomerReport(Long customerId);

    List<ProductReportDTO> getProductReport();

    List<ProductReportDTO> getTopProducts(Integer limit);

} 
