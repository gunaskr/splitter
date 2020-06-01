package com.splitter.transactionmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.github.mongobee.Mongobee;
import com.github.mongobee.exception.MongobeeException;
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
	
	@Bean
	public Mongobee mongobee() throws MongobeeException{
	  final String runnerUrl = mongoProperties.getHost() + ":" + mongoProperties.getPort() + "/" + mongoProperties.getDatabase(); 
	  final Mongobee runner = new Mongobee("mongodb://" + runnerUrl);
	  runner.setDbName(mongoProperties.getDatabase());         // host must be set if not set in URI
	  runner.setChangeLogsScanPackage("com.splitter.transactionmanagement.changelog"); // the package to be scanned for changesets
	  runner.setEnabled(true);
	  return runner;
	}

}
