package com.splitter.transactionmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import com.splitter.security.SecurityCommonsConfiguration;

@SpringBootApplication
@Import(SecurityCommonsConfiguration.class)
@EnableDiscoveryClient
public class TransactionManagementApplication {
	
	@Autowired
    private MongoProperties  mongoProperties;

	public static void main(String[] args) {
		SpringApplication.run(TransactionManagementApplication.class, args);
	}

}
