package com.example.mongoAggre.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.example.mongoAggre.dto.productResponseDto;
import com.example.mongoAggre.exception.BadDataException;
import com.example.mongoAggre.model.Product;
import com.example.mongoAggre.repo.ProductRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

    @Autowired
    public ProductRepo productRepo;

    @Autowired
    public MongoTemplate mongoTemplate;

    public String addProduct(List<Product> product) {

        if (product.isEmpty() || product == null) {
            return "Data not persist";
        } else {

            product.stream()
                    .map(p -> {
                        p.setCreatedAt(LocalDateTime.now());
                        p.setExpiry(OffsetDateTime.parse("2025-12-12T12:30:00.123Z").toLocalDateTime());
                        return p;
                    })
                    .map(productRepo::save)
                    .forEach(response -> log.info("Product {} added in the Schema!", response.getProduct()));
        }

        return "Data added Successfully!";
    }

    public List<productResponseDto> getProduct(String name) {
        MatchOperation match = Aggregation.match(Criteria.where("product").is(name));
        ProjectionOperation project = Aggregation.project("product", "price", "expiry", "createdAt");

        Aggregation aggregate = Aggregation.newAggregation(match, project);

        //unwind
        AggregationResults<productResponseDto> aggregation = mongoTemplate.aggregate(aggregate, "products",
                productResponseDto.class);

		if (aggregation.getMappedResults() == null) {
			throw new BadDataException("No data found!");
		}

		return aggregation.getMappedResults();

    }
}

