package com.vindya.mini_order_inventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI miniOrderInventoryOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mini Order & Inventory Management API")
                        .description("""
                                REST API for managing products, customers, orders and sales reports.

                                Features:
                                - Product catalogue with stock tracking and soft-delete (deactivation)
                                - Customer management with unique-email enforcement
                                - Order placement with stock validation and automatic inventory adjustment
                                - Order cancellation with inventory restoration
                                - Customer and product sales reports

                                All errors are returned in a consistent structure (timestamp, status, error, message).
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Vindya")
                                .email("vindyavasini3110@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
