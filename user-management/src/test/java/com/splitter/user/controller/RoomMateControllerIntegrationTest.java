package com.splitter.user.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import com.splitter.user.AbstractIntegrationTest;
import com.splitter.user.dto.RoomMateDTO;
import com.splitter.user.dto.UserDTO;
import com.splitter.user.model.Gender;
import com.splitter.user.model.User;

public class RoomMateControllerIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void testCreateRoomMates() throws URISyntaxException {
		List<RoomMateDTO> users = new ArrayList<>();
		RoomMateDTO user = new RoomMateDTO();
		user.setMobileNo("1");
		user.setAddedBy("2");
		user.setGender(Gender.FEMALE);
		user.setUsername("test");
		users.add(user);
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-auth-token", getToken());
		final RequestEntity<List<RoomMateDTO>> request = RequestEntity.post(new URI(getBaseUrl() + "/api/roommate"))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.headers(headers)
				.body(users);
		final ResponseEntity<List<User>> response = this.restTemplate.exchange(getBaseUrl() + "/api/roommate",
				HttpMethod.POST, request, new ParameterizedTypeReference<List<User>>() {
				});
		assertTrue(response.getBody().size() == 1);
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
	}
	
	@Test
	public void testGetRoomMates() throws URISyntaxException {
		/* sign up user */
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("user");
		userDTO.setGender(Gender.FEMALE);
		userDTO.setAddedBy("2");
		userDTO.setMobileNo("2");
		userDTO.setPassword("test");
		
		final RequestEntity<UserDTO> signUpRequest = RequestEntity.post(new URI(getBaseUrl() + "/api/signup"))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.body(userDTO);
		final ResponseEntity<User> signUpResponse = this.restTemplate.exchange(getBaseUrl() + "/api/signup",
				HttpMethod.POST, signUpRequest, User.class);
		
		assertTrue(signUpResponse.getStatusCode().equals(HttpStatus.OK), () -> "signup failed");
		
		/* add users */
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-auth-token", getToken());
		List<RoomMateDTO> users = new ArrayList<>();
		RoomMateDTO user = new RoomMateDTO();
		user.setMobileNo("1");
		user.setAddedBy("2");
		user.setGender(Gender.FEMALE);
		user.setUsername("test");
		users.add(user);
		
		final RequestEntity<List<RoomMateDTO>> addRoomMatesRequest = RequestEntity.post(new URI(getBaseUrl() + "/api/roommate"))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.headers(headers)
				.body(users);
		final ResponseEntity<List<User>> addRoomMatesResponse = this.restTemplate.exchange(getBaseUrl() + "/api/roommate",
				HttpMethod.POST, addRoomMatesRequest, new ParameterizedTypeReference<List<User>>() {
				});
		assertTrue(addRoomMatesResponse.getStatusCode().equals(HttpStatus.OK), () -> "adding users failed");
		
		HttpEntity getRoomMateEntity = new HttpEntity(headers);
		final ResponseEntity<List<User>> responseGet = this.restTemplate.exchange(getBaseUrl() + "/api/roommate/{mobileNo}",
				HttpMethod.GET, getRoomMateEntity, new ParameterizedTypeReference<List<User>>() {
				},
				"2");
		assertTrue(responseGet.getStatusCode().equals(HttpStatus.OK));
		assertTrue(responseGet.getBody().size() == 1);
	}
	
	@Test
	public void deleteRoomMate() throws URISyntaxException {
		/* add users */
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-auth-token", getToken());
		List<RoomMateDTO> users = new ArrayList<>();
		RoomMateDTO user = new RoomMateDTO();
		user.setMobileNo("1");
		user.setAddedBy("2");
		user.setGender(Gender.FEMALE);
		user.setUsername("test");
		users.add(user);
		
		final RequestEntity<List<RoomMateDTO>> addRoomMatesRequest = RequestEntity.post(new URI(getBaseUrl() + "/api/roommate"))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.headers(headers)
				.body(users);
		final ResponseEntity<List<User>> addRoomMatesResponse = this.restTemplate.exchange(getBaseUrl() + "/api/roommate",
				HttpMethod.POST, addRoomMatesRequest, new ParameterizedTypeReference<List<User>>() {
				});
		assertTrue(addRoomMatesResponse.getStatusCode().equals(HttpStatus.OK), () -> "adding users failed");
		
		/* check for room mate is present */
		HttpEntity getRoomMateEntity = new HttpEntity(headers);
		ResponseEntity<List<User>> responseGet = this.restTemplate.exchange(getBaseUrl() + "/api/roommate/{mobileNo}",
				HttpMethod.GET, getRoomMateEntity, new ParameterizedTypeReference<List<User>>() {
				},
				"2");
		assertTrue(responseGet.getStatusCode().equals(HttpStatus.OK));
		assertTrue(responseGet.getBody().size() == 1);
		
		/* delete the roomMate */
		final ResponseEntity<List<User>> responseDelete = this.restTemplate.exchange(getBaseUrl() + "/api/roommate/{mobileNo}/{addedBy}",
				HttpMethod.DELETE, getRoomMateEntity, new ParameterizedTypeReference<List<User>>() {
				},
				"1","2");
		assertTrue(responseDelete.getStatusCode().equals(HttpStatus.OK));
		
		/* check for the room mate */
		responseGet = this.restTemplate.exchange(getBaseUrl() + "/api/roommate/{mobileNo}",
				HttpMethod.GET, getRoomMateEntity, new ParameterizedTypeReference<List<User>>() {
				},
				"2");
		assertTrue(responseGet.getStatusCode().equals(HttpStatus.OK));
		assertTrue(responseGet.getBody().isEmpty(), () -> "room mate is not deleted properly");
	}

}
