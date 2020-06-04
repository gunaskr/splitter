package com.splitter.user.controller;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.splitter.user.repository.UserRepository;

public class RoomMateControllerIntegrationTest extends AbstractIntegrationTest {
	
	@Autowired
	private UserRepository userRepository;

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
				.header("x-auth-token", getToken())
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
		User user2 = new User();
		user2.setUsername("user");
		user2.setGender(Gender.FEMALE);
		user2.setAddedBy("2");
		user2.setMobileNo("2");
		user2.setPassword("test1");
		
		User savedUser2 = userRepository.save(user2);
		
		User user2_1 = new User();
		user2_1.setMobileNo("1");
		user2_1.setAddedBy("2");
		user2_1.setGender(Gender.FEMALE);
		user2_1.setUsername("test2_1");
		
		User savedUser2_1 = userRepository.save(user2_1);
		
		User user3_2 = new User();
		user3_2.setMobileNo("2");
		user3_2.setAddedBy("3");
		user3_2.setGender(Gender.FEMALE);
		user3_2.setUsername("test3_2");
		
		User savedUser3_2 = userRepository.save(user3_2);
		
		User user3 = new User();
		user3.setMobileNo("3");
		user3.setAddedBy("3");
		user3.setGender(Gender.FEMALE);
		user3.setUsername("test3");
		
		User savedUser3 = userRepository.save(user3);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-auth-token", getToken());
		HttpEntity getRoomMateEntity = new HttpEntity(headers);
		final ResponseEntity<List<User>> responseGet = this.restTemplate.exchange(getBaseUrl() + "/api/roommate/{mobileNo}",
				HttpMethod.GET, getRoomMateEntity, new ParameterizedTypeReference<List<User>>() {
				},
				user2.getMobileNo());
		assertTrue(responseGet.getStatusCode().equals(HttpStatus.OK));
		assertEquals("room mates returned is not of correct size", 2, responseGet.getBody().size());
		List<User> body = responseGet.getBody();
		assertTrue("should contain the name of the user who added the current user",
				body.stream().anyMatch(user -> {
					return user.getUsername().equalsIgnoreCase(savedUser3.getUsername());
				}));
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
				.header("x-auth-token", getToken())
				.body(users);
		final ResponseEntity<List<User>> addRoomMatesResponse = this.restTemplate.exchange(getBaseUrl() + "/api/roommate",
				HttpMethod.POST, addRoomMatesRequest, new ParameterizedTypeReference<List<User>>() {
				});
		assertTrue("adding users failed", addRoomMatesResponse.getStatusCode().equals(HttpStatus.OK));
		
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
		assertTrue("room mate is not deleted properly", responseGet.getBody().isEmpty());
	}

}
