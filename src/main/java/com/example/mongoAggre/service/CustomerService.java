package com.example.mongoAggre.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.example.mongoAggre.dto.CustomerReqDto;
import com.example.mongoAggre.model.Customer;
import com.example.mongoAggre.model.Product;
import com.example.mongoAggre.repo.CustomerRepo;
import com.example.mongoAggre.repo.ProductRepo;
import com.mongodb.BasicDBObject;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerService {

	@Autowired
	public CustomerRepo customerRepo;

	@Autowired
	public ProductRepo productRepo;

	@Autowired
	public MongoTemplate mongoTemplate;
	
	public String addToCart(CustomerReqDto customerReqDto) {

		List<Product> products = new ArrayList<>();
		
		for(String p : customerReqDto.getProduct()) {
			Product product = productRepo.findByProduct(p);
			if(product != null) {
				products.add(product);	
			}else {
				log.info("Product not found");
			}
		}

		if (products.isEmpty() || products == null) {
			log.error("product not found");
			return "Not found!";
		}

		if (!products.isEmpty() || products != null) {

			Customer customer = customerRepo.findByName(customerReqDto.getName());

			int total = 0;
			
			total = products.stream().mapToInt(Product::getPrice).sum();

			if (customer == null) {
				customer = new Customer();
				customer.setName(customerReqDto.getName());
			}

			customer.setProducts(products);
			customer.setCreatedAt(LocalDateTime.now());
			customer.setTotalBill( (double) total );
			customerRepo.save(customer);
		}

		return "Product brought successfully";

	}

	public List<Customer> getData(String name) {

		MatchOperation match = Aggregation.match(Criteria.where("name").regex(name, "i"));
		
		ProjectionOperation project = Aggregation.project("name", "products", "createdAt", "totalBill")
				.and("products").size().as("productCount");
		
		Aggregation aggregations = Aggregation.newAggregation(match, project);
		AggregationResults<Customer> result = mongoTemplate.aggregate(aggregations, "customer", Customer.class);
	
		GroupOperation count = Aggregation.group("products").first("products").as("products").count().as("totalRecords");
		
		Aggregation countAggregation = Aggregation.newAggregation(count);
		
		List<BasicDBObject> totalRecords = mongoTemplate.aggregate(countAggregation, "customer", BasicDBObject.class).getMappedResults();
		log.info("Total count of product {}", totalRecords.size());
		
		return result.getMappedResults();
	}

}
