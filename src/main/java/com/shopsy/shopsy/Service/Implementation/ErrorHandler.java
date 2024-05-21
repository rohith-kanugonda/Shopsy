package com.shopsy.shopsy.Service.Implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shopsy.shopsy.Dto.ProductRequest;
import com.shopsy.shopsy.Dto.ProductResponse;

@Service
public class ErrorHandler {
    
      public List<ProductResponse> createErrorResponses(List<ProductRequest> productRequests, String errorMessage) {
        List<ProductResponse> errorResponses = new ArrayList<>();

        for (ProductRequest productRequest : productRequests) {
            ProductResponse errorResponse = ProductResponse.builder()
                    .statusCode(500)
                    .responseMessage("Error processing product with name '" + productRequest.getProductName()
                            + "' and size '" + productRequest.getSize() + "': " + errorMessage)
                    .build();
            errorResponses.add(errorResponse);
        }

        return errorResponses;
    }
}
