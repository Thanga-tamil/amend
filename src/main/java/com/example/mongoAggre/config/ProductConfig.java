package com.example.mongoAggre.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class ProductConfig {

    @Bean
    public MongoClient mongoClient() {
        ConnectionString uri = new ConnectionString("mongodb://localhost:27017/store");
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(uri).build();
        return MongoClients.create(settings);
    }
}
