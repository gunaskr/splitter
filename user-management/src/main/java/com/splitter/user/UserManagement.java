package com.splitter.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import com.splitter.security.SecurityCommonsConfiguration;


@SpringBootApplication
@Import(SecurityCommonsConfiguration.class)
@EnableDiscoveryClient
public class UserManagement {

    public static void main(final String[] args) {
        SpringApplication.run(UserManagement.class, args);
	}
    
}
