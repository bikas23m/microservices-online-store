package com.bikas.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bikas.productservice.model.Product;

@Repository
public interface ProductRepo extends MongoRepository<Product,String>{

}
