package com.splitter.user;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.TestPropertySource;

import com.splitter.security.model.Authority;
import com.splitter.security.service.JWTUserDetails;
import com.splitter.security.service.TokenService;

@SpringBootTest(classes = {Application.class}, 
webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application-test.properties")
//@Transactional("transactionManager")
public abstract class AbstractIntegrationTest {
	
	@LocalServerPort
    private int port;
	
	@Autowired
    protected TestRestTemplate restTemplate;
    
    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @BeforeEach
    public void beforeTest() {
    	mongoTemplate.getDb().drop();
    }
    
    
    protected String getToken() {
    	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    	authorities.add(Authority.ROLE_USER);
    	JWTUserDetails userDetails = new JWTUserDetails(authorities, 
    			"test", false, false, false, true);
    	return tokenService.getToken(userDetails);
    }
    
    protected String getBaseUrl() {
    	return "http://localhost:" + port ;
    }

}
