package com.splitter.user.controller;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.splitter.user.dto.UserDTO;
import com.splitter.user.model.Gender;
import com.splitter.user.model.User;
import com.splitter.user.model.User.CompositeKey;
import com.splitter.user.repository.UserRepository;

public class RoomMateControllerIntegrationTest extends AbstractIntegrationTest {
	
	@Autowired
	private UserRepository userRepository;

	@Test
	public void testCreateRoomMates() throws URISyntaxException {
		List<UserDTO> users = new ArrayList<>();
		UserDTO user = new UserDTO();
		user.setMobileNo("1");
		user.setAddedBy("2");
		user.setGender(Gender.FEMALE);
		user.setUsername("test");
		users.add(user);
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-auth-token", getToken(null));
		final RequestEntity<List<UserDTO>> request = RequestEntity.post(new URI(getBaseUrl() + "/api/v1/roommate"))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.header("x-auth-token", getToken(null))
				.body(users);
		final ResponseEntity<List<User>> response = this.restTemplate.exchange(getBaseUrl() + "/api/v1/roommate",
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
		CompositeKey key = new CompositeKey();
		key.setAddedBy("2");
		key.setMobileNo("2");
		user2.setCompositeKey(key);
		user2.setPassword("test1");
		
		userRepository.save(user2);
		
		User user2_1 = new User();
		CompositeKey key2_1 = new CompositeKey();
		key2_1.setMobileNo("1");
		key2_1.setAddedBy("2");
		user2_1.setCompositeKey(key2_1);
		user2_1.setGender(Gender.FEMALE);
		user2_1.setUsername("test2_1");
		
		userRepository.save(user2_1);
		
		User user3_2 = new User();
		CompositeKey key3_2 = new CompositeKey();
		key3_2.setMobileNo("2");
		key3_2.setAddedBy("3");
		user3_2.setCompositeKey(key3_2);
		user3_2.setGender(Gender.FEMALE);
		user3_2.setUsername("test3_2");
		
		userRepository.save(user3_2);
		
		User user3 = new User();
		CompositeKey key3 = new CompositeKey();
		key3.setMobileNo("3");
		key3.setAddedBy("3");
		user3.setCompositeKey(key3);
		user3.setGender(Gender.FEMALE);
		user3.setUsername("test3");
		
		User savedUser3 = userRepository.save(user3);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-auth-token", getToken(null));
		HttpEntity<?> getRoomMateEntity = new HttpEntity<>(headers);
		final ResponseEntity<List<User>> responseGet = this.restTemplate.exchange(getBaseUrl() + "/api/v1/roommate/{mobileNo}",
				HttpMethod.GET, getRoomMateEntity, new ParameterizedTypeReference<List<User>>() {
				},
				user2.getCompositeKey().getMobileNo());
		assertTrue(responseGet.getStatusCode().equals(HttpStatus.OK));
		assertEquals("room mates returned is not of correct size", 3, responseGet.getBody().size());
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
		headers.add("x-auth-token", getToken(null));
		List<UserDTO> users = new ArrayList<>();
		UserDTO user = new UserDTO();
		user.setMobileNo("1");
		user.setAddedBy("2");
		user.setGender(Gender.FEMALE);
		user.setUsername("test");
		users.add(user);
		
		final RequestEntity<List<UserDTO>> addRoomMatesRequest = RequestEntity.post(new URI(getBaseUrl() + "/api/v1/roommate"))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.header("x-auth-token", getToken(null))
				.body(users);
		final ResponseEntity<List<User>> addRoomMatesResponse = this.restTemplate.exchange(getBaseUrl() + "/api/v1/roommate",
				HttpMethod.POST, addRoomMatesRequest, new ParameterizedTypeReference<List<User>>() {
				});
		assertTrue("adding users failed", addRoomMatesResponse.getStatusCode().equals(HttpStatus.OK));
		
		/* check for room mate is present */
		HttpEntity<?> getRoomMateEntity = new HttpEntity<>(headers);
		ResponseEntity<List<User>> responseGet = this.restTemplate.exchange(getBaseUrl() + "/api/v1/roommate/{mobileNo}",
				HttpMethod.GET, getRoomMateEntity, new ParameterizedTypeReference<List<User>>() {
				},
				"2");
		assertTrue(responseGet.getStatusCode().equals(HttpStatus.OK));
		assertTrue(responseGet.getBody().size() == 1);
		
		/* delete the roomMate */
		final ResponseEntity<List<User>> responseDelete = this.restTemplate.exchange(getBaseUrl() + "/api/v1/roommate/{mobileNo}/{addedBy}",
				HttpMethod.DELETE, getRoomMateEntity, new ParameterizedTypeReference<List<User>>() {
				},
				"1","2");
		assertTrue(responseDelete.getStatusCode().equals(HttpStatus.OK));
		
		/* check for the room mate */
		responseGet = this.restTemplate.exchange(getBaseUrl() + "/api/v1/roommate/{mobileNo}",
				HttpMethod.GET, getRoomMateEntity, new ParameterizedTypeReference<List<User>>() {
				},
				"2");
		assertTrue(responseGet.getStatusCode().equals(HttpStatus.OK));
		assertTrue("room mate is not deleted properly", responseGet.getBody().isEmpty());
	}
	
	@Test
	public void testIfUserCanEnterReverseDuplicateRoommate() throws URISyntaxException {
		User user1 = new User();
		user1.setUsername("user1");
		CompositeKey key1 = new CompositeKey();
		key1.setAddedBy("1");
		key1.setMobileNo("1");
		user1.setCompositeKey(key1);
		
		User user2 = new User();
		user2.setUsername("user2");
		CompositeKey key2 = new CompositeKey();
		key2.setAddedBy("2");
		key2.setMobileNo("2");
		user2.setCompositeKey(key2);
		
		User user1_2 = new User();
		user1_2.setUsername("user1_2");
		CompositeKey key1_2 = new CompositeKey();
		key1_2.setAddedBy("1");
		key1_2.setMobileNo("2");
		user1_2.setCompositeKey(key1_2);
	    
		this.userRepository.saveAll(Arrays.asList(user1, user2, user1_2));
		
		List<UserDTO> users = new ArrayList<>();
		UserDTO user = new UserDTO();
		user.setMobileNo("1");
		user.setAddedBy("2");
		user.setUsername("user2_1");
		users.add(user);
		HttpHeaders headers = new HttpHeaders();
		String token = getToken("2");
		headers.add("x-auth-token", token);
		final RequestEntity<List<UserDTO>> request = RequestEntity.post(new URI(getBaseUrl() + "/api/v1/roommate"))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.header("x-auth-token", token)
				.body(users);
		final ResponseEntity<String> response = this.restTemplate.exchange(getBaseUrl() + "/api/v1/roommate",
				HttpMethod.POST, request, new ParameterizedTypeReference<String>() {
				});
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
	}

}
