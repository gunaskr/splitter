package com.splitter.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import com.splitter.security.SecurityCommonsConfiguration;


@SpringBootApplication
@Import(SecurityCommonsConfiguration.class)
@EnableDiscoveryClient
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
	}
}
