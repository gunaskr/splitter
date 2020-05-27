package com.splitter.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@ComponentScan({
	"com.splitter.security.config",
	"com.splitter.security.exception.handler",
	"com.splitter.security.service"
})
public class SecurityCommonsConfiguration {
	
	 @Bean
	    public FilterRegistrationBean<CorsFilter> corsFilter() {
	        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        final CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(Boolean.TRUE);
	        config.addAllowedOrigin("*");
	        config.addAllowedHeader("*");
	        config.addAllowedMethod("*");
	        source.registerCorsConfiguration("/**", config);
	        final FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
	        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
	        return bean;
	    }

}
