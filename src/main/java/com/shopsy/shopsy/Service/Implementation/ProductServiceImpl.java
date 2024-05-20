package com.shopsy.shopsy.Service.Implementation;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shopsy.shopsy.Dto.ProductInfo;
import com.shopsy.shopsy.Dto.ProductRequest;
import com.shopsy.shopsy.Dto.ProductResponse;
import com.shopsy.shopsy.Entity.Products;
import com.shopsy.shopsy.Repository.ProductRepo;
import com.shopsy.shopsy.Service.AppInterfaces.ProductsService;

@Service
public class ProductServiceImpl implements ProductsService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<List<ProductResponse>> addProducts(List<ProductRequest> productRequests) {
        List<ProductResponse> productResponses = new ArrayList<>();

        for (ProductRequest productRequest : productRequests) {
            List<Products> existingProductByNameAndSize = productRepo
                    .findByProductNameAndSize(productRequest.getProductName(), productRequest.getSize());

            if (!existingProductByNameAndSize.isEmpty()) {
                ProductResponse errorResponse = ProductResponse.builder()
                        .statusCode(400)
                        .responseMessage("Product with name '" + productRequest.getProductName() + "' and size '"
                                + productRequest.getSize() + "' already exists!")
                        .build();
                productResponses.add(errorResponse);
                continue;
            }

            Products products = Products.builder()
                    .productName(productRequest.getProductName())
                    .description(productRequest.getDescription())
                    .pricePerProduct(productRequest.getPricePerProduct())
                    .quantity(productRequest.getQuantity())
                    .size(productRequest.getSize())
                    .build();
            Products savedProduct = productRepo.save(products);

            ProductResponse successResponse = ProductResponse.builder()
                    .statusCode(200)
                    .responseMessage("Success")
                    .productInfo(modelMapper.map(savedProduct, ProductInfo.class))
                    .build();
            productResponses.add(successResponse);
        }

        return ResponseEntity.ok(productResponses);
    }

    @Override
    public ResponseEntity<ProductResponse> updateProduct(ProductRequest productRequest) {
        return null;
    }

}
