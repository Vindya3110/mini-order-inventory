package com.vindya.mini_order_inventory.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table (name = "products")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class Product {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank 
    private String name;

    @NotBlank
    private String category;

    @Positive 
    private BigDecimal price;

    @PositiveOrZero 
    private Integer availableQuantity;

    private Boolean active=true;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist 
    public void onCreate() {
        createdAt=LocalDateTime.now();
        updatedAt=LocalDateTime.now();
    }

    @PreUpdate 
    public void onUpdate() {
        updatedAt=LocalDateTime.now();
    }
}