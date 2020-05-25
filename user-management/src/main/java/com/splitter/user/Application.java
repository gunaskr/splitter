package com.splitter.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

import com.splitter.security.SecurityCommonsConfiguration;


@SpringBootApplication
@Import(SecurityCommonsConfiguration.class)
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
	}
    
    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
}
