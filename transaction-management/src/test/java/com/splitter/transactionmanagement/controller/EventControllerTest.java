package com.splitter.transactionmanagement.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.ModCheck;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.convert.JodaTimeConverters.LocalDateTimeToDateConverter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import com.splitter.transactionmanagement.AbstractIntegrationTest;
import com.splitter.transactionmanagement.controller.dto.EventVO;
import com.splitter.transactionmanagement.controller.dto.TransactionVO;
import com.splitter.transactionmanagement.model.CategoryType;
import com.splitter.transactionmanagement.model.Event;
import com.splitter.transactionmanagement.model.Transaction;
import com.splitter.transactionmanagement.model.User;
import com.splitter.transactionmanagement.repository.EventRepository;
import com.splitter.transactionmanagement.repository.TransactionRepository;
import com.splitter.transactionmanagement.repository.UserManagementClient;

public class EventControllerTest extends AbstractIntegrationTest {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@MockBean
	private UserManagementClient userManagementClient;

	@Test
	public void testCreateEvent() throws Exception {
		String primaryUser = "1";
		
		String secondUser = "2";
		
		String thirdUser = "3";
		
		EventVO eventRequest = new EventVO();
		eventRequest.setAmountSpent(BigDecimal.valueOf(100));
		eventRequest.setCategory(CategoryType.FOOD);
		User primaryUserObject = new User();
		primaryUserObject.setMobileNo(primaryUser);
		eventRequest.setUser(primaryUserObject);
		
		TransactionVO r1 = new TransactionVO();
		r1.setFromUser(primaryUserObject);
		r1.setToUser(primaryUserObject);
		r1.setAmount(BigDecimal.valueOf(33.33f));
		eventRequest.getTransactions().add(r1);
		
		User secondUserObject = new User();
		secondUserObject.setMobileNo(secondUser);
		
		TransactionVO r2 = new TransactionVO();
		r2.setFromUser(secondUserObject);
		r2.setToUser(primaryUserObject);
		r2.setAmount(BigDecimal.valueOf(33.33f));
		eventRequest.getTransactions().add(r2);
		
		User thirdUserObject = new User();
		thirdUserObject.setMobileNo(thirdUser);
		
		TransactionVO r3 = new TransactionVO();
		r3.setFromUser(thirdUserObject);
		r3.setToUser(primaryUserObject);
		r3.setAmount(BigDecimal.valueOf(33.33f));
		eventRequest.getTransactions().add(r3);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-auth-token", getToken());
		final RequestEntity<EventVO> request = RequestEntity.post(new URI(getBaseUrl() + "/api/event"))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.header("x-auth-token", getToken())
				.body(eventRequest);
		final ResponseEntity<EventVO> response = this.restTemplate.exchange(getBaseUrl() + "/api/event",
				HttpMethod.POST, request, new ParameterizedTypeReference<EventVO>() {
				});
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		
	}
	
	@Test
	public void testFindEventById() {
		String user1 = "1";
		String user2 = "2";
		String user3 = "3";
		List<User> users = new ArrayList<>();
		User user1Object = new User();
		user1Object.setMobileNo(user1);
		users.add(user1Object);
		
		User user2Object = new User();
		user2Object.setMobileNo(user2);
		users.add(user2Object);
		
		User user3Object = new User();
		user3Object.setMobileNo(user3);
		users.add(user3Object);
		
		Event event1 = new Event();
		event1.setAmountSpent(BigDecimal.valueOf(100));
		event1.setCategory(CategoryType.FOOD);
		event1.setUserId(user1);
		event1.setName("event1");
		
		Event savedEvent1 = eventRepository.save(event1);
		
		Transaction t1 = new Transaction();
		t1.setAmount(BigDecimal.valueOf(50));
		t1.setFromUserId(user1);
		t1.setToUserId(user1);
		t1.setEvent(savedEvent1);
		transactionRepository.save(t1);
		
		Transaction t2 = new Transaction();
		t2.setAmount(BigDecimal.valueOf(50));
		t2.setFromUserId(user2);
		t2.setToUserId(user1);
		t2.setEvent(savedEvent1);
		transactionRepository.save(t2);
		
		Event event2 = new Event();
		event2.setAmountSpent(BigDecimal.valueOf(100));
		event2.setCategory(CategoryType.TRAVEL);
		event2.setUserId(user3);
		
		Event savedEvent2 = eventRepository.save(event2);
		
		Transaction t3 = new Transaction();
		t3.setAmount(BigDecimal.valueOf(50));
		t3.setFromUserId(user3);
		t3.setToUserId(user3);
		t3.setEvent(savedEvent2);
		transactionRepository.save(t3);
		
		Transaction t4 = new Transaction();
		t4.setAmount(BigDecimal.valueOf(50));
		t4.setFromUserId(user1);
		t4.setToUserId(user3);
		t4.setEvent(savedEvent2);
		transactionRepository.save(t4);
		
		HttpHeaders headers = new HttpHeaders();
		String token = getToken();
		headers.add("x-auth-token", token);
		Mockito.when(userManagementClient.getRoomMates("test", token)).thenReturn(users);
		HttpEntity entityWithHeader = new HttpEntity(headers);
		final ResponseEntity<EventVO> responseGet = this.restTemplate.exchange(getBaseUrl() + "/api/event/{eventId}",
				HttpMethod.GET, entityWithHeader, new ParameterizedTypeReference<EventVO>() {
				},
				savedEvent1.getId());
		assertTrue(responseGet.getStatusCode().equals(HttpStatus.OK));
		assertEquals("event is not same", "event1", responseGet.getBody().getName());
		assertEquals("mobile no is not same", "1", responseGet.getBody().getUser().getMobileNo());
		assertEquals("transaction size is not ame", 2, responseGet.getBody().getTransactions().size());
		
		final ResponseEntity<List<EventVO>> responseFilter = this.restTemplate.exchange(getBaseUrl() + "/api/event?fromUserId={fromUserId}&toUserId={toUserId}",
				HttpMethod.GET, entityWithHeader, new ParameterizedTypeReference<List<EventVO>>() {
				},
				user1, user1);
		assertTrue(responseFilter.getStatusCode().equals(HttpStatus.OK));
		assertEquals("events size is not same", 2, responseFilter.getBody().size());
	}
	
	@Test
	public void testTimeSaving() {
		Event event1 = new Event();
		event1.setAmountSpent(BigDecimal.valueOf(100));
		event1.setCategory(CategoryType.FOOD);
		event1.setUserId("1");
		event1.setName("event1");
		LocalDateTime testTime = LocalDateTime.now();
		event1.setCreatedAt(testTime);
		
		Event savedEntity = eventRepository.save(event1);
		
		Optional<Event> findById = eventRepository.findById(savedEntity.getId());
		
		assertTrue("item not present", findById.isPresent());
		assertTrue("dates dont match", findById.get().getCreatedAt().isEqual(testTime));
	}
}