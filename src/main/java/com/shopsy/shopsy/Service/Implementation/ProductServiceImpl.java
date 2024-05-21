package com.shopsy.shopsy.Service.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private ErrorHandler errorHandler;

    @Override
    public ResponseEntity<List<ProductResponse>> addProducts(List<ProductRequest> productRequests) {
        List<ProductResponse> productResponses = new ArrayList<>();

        try {
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
        } catch (Exception e) {
            return ResponseEntity.status(500).body(errorHandler.createErrorResponses(productRequests, e.getMessage()));
        }
    }

    public ResponseEntity<ProductResponse> updateProduct(Long productId, ProductRequest productRequest) {
        try {
            // Find the existing product by ID
            Optional<Products> optionalProduct = productRepo.findById(productId);
            if (optionalProduct.isEmpty()) {
                return ResponseEntity.badRequest().body(ProductResponse.builder()
                        .statusCode(400)
                        .responseMessage("Product with ID '" + productId + "' does not exist!")
                        .build());
            }

            Products existingProduct = optionalProduct.get();

            // Check if there's another product with the same name and size
            List<Products> productsByNameAndSize = productRepo.findByProductNameAndSize(
                    productRequest.getProductName(), productRequest.getSize());

            boolean isDuplicate = productsByNameAndSize.stream()
                    .anyMatch(product -> !product.getProductId().equals(existingProduct.getProductId()));

            if (isDuplicate) {
                // Update non-conflicting fields
                existingProduct.setDescription(productRequest.getDescription());
                existingProduct.setPricePerProduct(productRequest.getPricePerProduct());
                existingProduct.setQuantity(productRequest.getQuantity());

                Products updatedProduct = productRepo.save(existingProduct);

                return ResponseEntity.badRequest().body(ProductResponse.builder()
                        .statusCode(400)
                        .responseMessage("Another product with name '" + productRequest.getProductName() +
                                "' and size '" + productRequest.getSize()
                                + "' already exists! Non-conflicting fields updated.")
                        .productInfo(modelMapper.map(updatedProduct, ProductInfo.class))
                        .build());
            }

            // Update all fields
            existingProduct.setProductName(productRequest.getProductName());
            existingProduct.setDescription(productRequest.getDescription());
            existingProduct.setPricePerProduct(productRequest.getPricePerProduct());
            existingProduct.setQuantity(productRequest.getQuantity());
            existingProduct.setSize(productRequest.getSize());

            Products updatedProduct = productRepo.save(existingProduct);

            return ResponseEntity.ok(ProductResponse.builder()
                    .statusCode(200)
                    .responseMessage("Product updated successfully")
                    .productInfo(modelMapper.map(updatedProduct, ProductInfo.class))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ProductResponse.builder()
                    .statusCode(500)
                    .responseMessage("An error occurred while updating the product: " + e.getMessage())
                    .build());
        }
    }

    // Get all products
    public ResponseEntity<List<ProductInfo>> getAllProducts() {
        List<Products> products = productRepo.findAll();
        List<ProductInfo> productInfos = products.stream()
                .map(product -> modelMapper.map(product, ProductInfo.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(productInfos);
    }

    // Find product by ID
    public ResponseEntity<Object> findProductById(Long productId) {
        Optional<Products> product = productRepo.findById(productId);
        if (product.isPresent()) {
            ProductInfo productInfo = modelMapper.map(product.get(), ProductInfo.class);
            return ResponseEntity.ok(productInfo);
        } else {
            return ResponseEntity.status(404).body("Product with ID '" + productId + "' not found.");
        }
    }

    // Delete product by ID
    public ResponseEntity<Object> deleteProductById(Long productId) {
        if (productRepo.existsById(productId)) {
            productRepo.deleteById(productId);
            return ResponseEntity.ok("Product with ID '" + productId + "' deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Product with ID '" + productId + "' not found.");
        }
    }

}
