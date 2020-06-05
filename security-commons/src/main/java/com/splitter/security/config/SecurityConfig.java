package com.splitter.security.config;

import com.splitter.security.filter.AuthenticationTokenFilter;
import com.splitter.security.service.TokenAuthenticationService;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    protected SecurityConfig(final TokenAuthenticationService tokenAuthenticationService) {
        super();
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/auth").permitAll()
                .antMatchers("/api/signup").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .anyRequest().hasAuthority("ROLE_USER")
                .and()
                .addFilterBefore(new AuthenticationTokenFilter(tokenAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
    }
    
    @Bean
	public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
	    return new BeanFactoryPostProcessor() {
	        @Override
	        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	            beanFactory.registerScope("thread", new SimpleThreadScope());
	        }
	    };
	}
}
