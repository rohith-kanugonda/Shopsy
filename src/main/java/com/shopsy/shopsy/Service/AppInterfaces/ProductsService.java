package com.shopsy.shopsy.Service.AppInterfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.shopsy.shopsy.Dto.ProductInfo;
import com.shopsy.shopsy.Dto.ProductRequest;
import com.shopsy.shopsy.Dto.ProductResponse;

public interface ProductsService {

    ResponseEntity<List<ProductResponse>> addProducts(List<ProductRequest> productRequest);

    ResponseEntity<ProductResponse> updateProduct(Long productId, ProductRequest productRequest);

    ResponseEntity<Object> findProductById(Long productId);

    ResponseEntity<List<ProductInfo>> getAllProducts();

    ResponseEntity<Object> deleteProductById(Long productId);

}
