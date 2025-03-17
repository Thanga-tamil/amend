package com.example.mongoAggre.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mongoAggre.dto.productResponseDto;
import com.example.mongoAggre.model.Product;
import com.example.mongoAggre.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    public ProductService productService;

    @PostMapping("/add-products")
    public String addProduct(@RequestBody List<Product> product) {
        return productService.addProduct(product);
    }

    @GetMapping("/get-product")
    public List<productResponseDto> getProduct(@RequestParam String product) {

        return productService.getProduct(product);
    }

    //develop branch changes
    
    //foo
}
