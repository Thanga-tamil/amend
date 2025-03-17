package com.example.mongoAggre.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.mongoAggre.model.Product;

@Repository
public interface ProductRepo extends MongoRepository<Product, String> {

	Product findByProduct(String name);

}
