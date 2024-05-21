package com.shopsy.shopsy.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopsy.shopsy.Dto.ProductRequest;
import com.shopsy.shopsy.Dto.ProductResponse;
import com.shopsy.shopsy.Service.AppInterfaces.ProductsService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("products")
@AllArgsConstructor
public class ProductController {

    @Autowired
    private ProductsService productsService;

    @PostMapping("addproduct")
    public ResponseEntity<List<ProductResponse>> addProducts(@RequestBody List<ProductRequest> productRequest) {
        return productsService.addProducts(productRequest);
    }

  
    @PutMapping("/updateproduct/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long productId, @RequestBody ProductRequest productRequest) {
        return productsService.updateProduct(productId, productRequest);
    }

}
