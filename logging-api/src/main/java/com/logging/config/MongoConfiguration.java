package com.logging.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;

@Configuration
public class MongoConfiguration {

    private @Value("${logging.mongodb.host}") String dbHost;

    private @Value("${logging.mongodb.port}") int dbPort;

    private @Value("${logging.mongodb.name}") String dbName;

    public @Bean MongoClient mongoClient() throws Exception {
        return new MongoClient(dbHost, dbPort);
    }

    public @Bean MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(mongoClient(), dbName);
    }

    public @Bean MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }

    public @Bean ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}