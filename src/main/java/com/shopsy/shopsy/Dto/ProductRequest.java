package com.shopsy.shopsy.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    
    private Long productId;
    private String productName;
    private String description;
    private double pricePerProduct;
    private int quantity;
    private int size;
    
}
