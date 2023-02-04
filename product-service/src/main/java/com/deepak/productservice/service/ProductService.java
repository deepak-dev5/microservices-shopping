package com.deepak.productservice.service;

import java.util.List;

import com.deepak.productservice.dto.ProductRequest;
import com.deepak.productservice.dto.ProductResponse;
import com.deepak.productservice.model.Product;
import com.deepak.productservice.repository.ProductRepository;
import static java.util.stream.Collectors.toList;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository prodRepository;



    public void CreateProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        prodRepository.save(product);
        log.info("Product {} is saved.",product.getId());
    }



    public List<ProductResponse> getAllProduct() {
        List<Product> products = prodRepository.findAll();
        //return products.stream().map(product -> mapToProductResponse(product)).collect(Collectors.toList());
        return products.stream().map(this::mapToProductResponse).toList();
    }

    public ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
