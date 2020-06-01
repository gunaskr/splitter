package com.splitter.security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
	"com.splitter.security.config",
	"com.splitter.security.exception.handler",
	"com.splitter.security.service"
})
public class SecurityCommonsConfiguration {}
