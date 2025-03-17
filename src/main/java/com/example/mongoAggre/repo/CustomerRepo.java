package com.example.mongoAggre.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.mongoAggre.model.Customer;

@Repository
public interface CustomerRepo extends MongoRepository<Customer, String>{

	Customer findByName(String name);

}
