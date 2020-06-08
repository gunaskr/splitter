package com.splitter.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.splitter.security.SecurityCommonsConfiguration;
import com.splitter.user.converter.dto.UserDTOToUser;


@SpringBootApplication
@Import(SecurityCommonsConfiguration.class)
@EnableDiscoveryClient
public class UserManagement {

    public static void main(final String[] args) {
        SpringApplication.run(UserManagement.class, args);
	}
    
    @Bean
    public UserDTOToUser userConverter() {
    	return new UserDTOToUser();
    }
    
    @Bean
    public UserDTOToUser roomMateConverter() {
    	return new UserDTOToUser(true);
    }
}
