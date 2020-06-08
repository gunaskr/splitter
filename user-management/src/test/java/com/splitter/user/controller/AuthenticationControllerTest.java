package com.splitter.user.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import com.splitter.user.AbstractIntegrationTest;
import com.splitter.user.dto.LoginDTO;
import com.splitter.user.dto.TokenDTO;
import com.splitter.user.dto.UserDTO;
import com.splitter.user.model.Gender;
import com.splitter.user.model.User;

public class AuthenticationControllerTest extends AbstractIntegrationTest {
	
	@Test
	public void testSignUpAndAuthentication() throws URISyntaxException {
		UserDTO user = new UserDTO();
		String userInfo = "1";
		user.setMobileNo(userInfo);
		user.setAddedBy(userInfo);
		user.setPassword(userInfo);
		user.setGender(Gender.FEMALE);
		user.setUsername("test");
		
		RequestEntity<UserDTO> signUpRequest = RequestEntity.post(new URI(getBaseUrl() + "/api/v1/signup"))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.body(user);
		final ResponseEntity<User> signUpResponse = this.restTemplate.exchange(getBaseUrl() + "/api/v1/signup",
				HttpMethod.POST, signUpRequest, new ParameterizedTypeReference<User>() {
				});
		assertTrue("unable to signup users", signUpResponse.getStatusCode().equals(HttpStatus.OK));
		
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername(userInfo);
		loginDTO.setPassword(userInfo);
		RequestEntity<LoginDTO> authRequest = RequestEntity.post(new URI(getBaseUrl() + "/api/v1/auth"))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.body(loginDTO);
		
		final ResponseEntity<TokenDTO> authResponse = this.restTemplate.exchange(getBaseUrl() + "/api/v1/auth",
				HttpMethod.POST, authRequest, new ParameterizedTypeReference<TokenDTO>() {
				});
		assertTrue("unable to login", authResponse.getStatusCode().equals(HttpStatus.OK));
		
		LoginDTO badRequestDTO = new LoginDTO();
		badRequestDTO.setUsername(userInfo);
		badRequestDTO.setPassword("2");
		RequestEntity<LoginDTO> badRequest = RequestEntity.post(new URI(getBaseUrl() + "/api/v1/auth"))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.body(badRequestDTO);
		
		final ResponseEntity<String> badReponse = this.restTemplate.exchange(getBaseUrl() + "/api/v1/auth",
				HttpMethod.POST, badRequest, new ParameterizedTypeReference<String>() {
				});
		assertEquals("not the right status code", HttpStatus.UNAUTHORIZED, badReponse.getStatusCode());
	}

}
