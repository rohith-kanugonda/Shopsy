package com.shopsy.shopsy.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="Products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Products {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    
    @NotNull
    private String productName;

    private String description;
    
    @NotNull
    private double pricePerProduct;
    
    @NotNull
    private int quantity;

    private int size;


}
