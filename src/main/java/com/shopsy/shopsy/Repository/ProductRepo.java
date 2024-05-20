package com.shopsy.shopsy.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopsy.shopsy.Entity.Products;

public interface ProductRepo extends JpaRepository<Products,Long>{

    Optional<Products> findByProductName(String productName);

    List<Products> findBySize(int size);

    List<Products> findByProductNameAndSize(String productName, int size);
        
}
