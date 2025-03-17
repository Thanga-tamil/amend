package com.example.mongoAggre.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class productResponseDto {

    private String product;

    private Integer price;

    private LocalDateTime createdAt;

    private LocalDateTime expiry;
    
    productResponseDto(String product, Integer price, LocalDateTime createdAt, LocalDateTime expiry) {
        this.product = product;
        this.price = price;
        this.createdAt = createdAt;
        this.expiry = expiry;
    }

}
