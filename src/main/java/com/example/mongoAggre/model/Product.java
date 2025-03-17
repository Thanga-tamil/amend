package com.example.mongoAggre.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "products")
@Data
public class Product {

    @Id
    private String id;

    private String product;

    private Integer price;

    private LocalDateTime expiry;

    private LocalDateTime createdAt;

}

