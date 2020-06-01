package com.splitter.transactionmanagement.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.models.auth.In;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Swagger Spring configuration.
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

	private static final String API_KEY_NAME = "x-auth-token";
	private static final String BASE_PACKAGE = "com.splitter.transactionmanagement.controller";

	private final SecurityReference securityReference = SecurityReference.builder()
			.reference(API_KEY_NAME)
			.scopes(new AuthorizationScope[0])
			.build();

	private final SecurityContext securityContext = SecurityContext.builder()
			.securityReferences(Arrays.asList(securityReference))
			.build();

	/**
	 * @return the Swagger documentation docket
	 */
	@Bean
	public Docket userApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.securitySchemes(Arrays.asList(apiKey()))
		        .securityContexts(Arrays.asList(securityContext));
	}

	private ApiKey apiKey() {
	    return new ApiKey(API_KEY_NAME, API_KEY_NAME, In.HEADER.toValue());
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfo(
				"splitter transaction-management REST API", 
				"This is the description of the splitter transaction-management REST API endpoints.", 
				"0.0", 
				"",
				ApiInfo.DEFAULT_CONTACT, 
				"", 
				"", 
				Collections.emptyList());
	}
}
