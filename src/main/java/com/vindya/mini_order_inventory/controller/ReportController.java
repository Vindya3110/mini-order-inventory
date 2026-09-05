package com.vindya.mini_order_inventory.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vindya.mini_order_inventory.dto.CustomerReportDTO;
import com.vindya.mini_order_inventory.dto.ProductReportDTO;
import com.vindya.mini_order_inventory.exception.ErrorResponse;
import com.vindya.mini_order_inventory.service.ReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Customer spending and product sales reports (active orders only)")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/customers/{customerId}")
    @Operation(summary = "Customer spending report",
            description = "Returns order count, total spent and average order value for a customer.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer report",
                    content = @Content(schema = @Schema(implementation = CustomerReportDTO.class),
                            examples = @ExampleObject(name = "Customer report", value = """
                                    {
                                      "customerName": "Alice",
                                      "numberOfOrders": 3,
                                      "totalAmountSpent": 150000.00,
                                      "averageOrderValue": 50000.00
                                    }"""))),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CustomerReportDTO> getCustomerReport(
            @Parameter(description = "Customer id", example = "1") @PathVariable Long customerId){
        return ResponseEntity.ok(reportService.getCustomerReport(customerId));
    }

    @GetMapping("/products")
    @Operation(summary = "Product sales report",
            description = "Returns units sold and revenue per product, ordered by units sold descending.")
    @ApiResponse(responseCode = "200", description = "Product sales report",
            content = @Content(array = @io.swagger.v3.oas.annotations.media.ArraySchema(
                    schema = @Schema(implementation = ProductReportDTO.class)),
                    examples = @ExampleObject(name = "Product report", value = """
                            [
                              {
                                "productId": 1,
                                "productName": "Laptop",
                                "quantitySold": 12,
                                "totalRevenue": 600000.00
                              }
                            ]""")))
    public ResponseEntity<List<ProductReportDTO>> getProductReport(){
        return ResponseEntity.ok(reportService.getProductReport());
    }

    @GetMapping("/top-products")
    @Operation(summary = "Top selling products",
            description = "Returns the best-selling products, limited to the given number (default 5).")
    @ApiResponse(responseCode = "200", description = "Top products by units sold")
    public ResponseEntity<List<ProductReportDTO>> getTopProducts(
            @Parameter(description = "Maximum number of products to return", example = "5")
            @RequestParam(defaultValue = "5") Integer limit){
         return ResponseEntity.ok(reportService.getTopProducts(limit));
    }
}
