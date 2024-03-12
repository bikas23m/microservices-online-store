package com.bikas.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.bikas.productservice.dto.ProductRequest;
import com.bikas.productservice.dto.ProductResponse;
import com.bikas.productservice.model.Product;
import com.bikas.productservice.repository.ProductRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

	private final ProductRepo productRepo;

	public void createProduct(ProductRequest productReqrest) {

		Product product = Product.builder().name(productReqrest.getName()).description(productReqrest.getDescription())
				.price(productReqrest.getPrice()).build();

		productRepo.save(product);
		log.info("Product {} is saved",product.getId());
	}

	public List<ProductResponse> getAllProduct() {
		List<Product> product = productRepo.findAll();
		return product.stream().map(this::mapToProductResponse).toList();
	}

	private ProductResponse mapToProductResponse(Product product) {
		
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getName())
				.price(product.getPrice())
				.build();
	}
}
