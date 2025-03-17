package com.example.mongoAggre.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "customer")
public class Customer {

	@Id
	private String id;
	
	private String name;
	
	private List<Product> products;
	
	private Double totalBill;
	
	private LocalDateTime createdAt;
}
