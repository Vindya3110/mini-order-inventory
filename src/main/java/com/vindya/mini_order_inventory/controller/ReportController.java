package com.vindya.mini_order_inventory.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vindya.mini_order_inventory.dto.CustomerReportDTO;
import com.vindya.mini_order_inventory.dto.ProductReportDTO;
import com.vindya.mini_order_inventory.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerReportDTO> getCustomerReport( @PathVariable Long customerId){
        return ResponseEntity.ok(reportService.getCustomerReport(customerId));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductReportDTO>> getProductReport(){
        return ResponseEntity.ok(reportService.getProductReport());
    }

    @GetMapping("/top-products")
    public ResponseEntity<List<ProductReportDTO>> getTopProducts(@RequestParam(defaultValue = "5") Integer limit){
         return ResponseEntity.ok(reportService.getTopProducts(limit));
    }
}