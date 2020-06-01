package com.splitter.transactionmanagement.controller;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.net.URI;
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

import com.splitter.transactionmanagement.AbstractIntegrationTest;
import com.splitter.transactionmanagement.controller.dto.EventVO;
import com.splitter.transactionmanagement.controller.dto.TransactionVO;
import com.splitter.transactionmanagement.model.CategoryType;
import com.splitter.transactionmanagement.model.Event;
import com.splitter.transactionmanagement.model.Gender;
import com.splitter.transactionmanagement.model.Transaction;
import com.splitter.transactionmanagement.model.User;
import com.splitter.transactionmanagement.repository.EventRepository;
import com.splitter.transactionmanagement.repository.TransactionRepository;

public class EventControllerTest extends AbstractIntegrationTest {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private EventRepository eventRepository;

	@Test
	public void testCreateEvent() throws Exception {
		User primaryUser = new User();
		primaryUser.setName("p1");
		primaryUser.setMobileNo("1");
		primaryUser.setAddedBy("1");
		primaryUser.setGender(Gender.FEMALE);
		
		User secondUser = new User();
		secondUser.setMobileNo("2");
		secondUser.setAddedBy("1");
		secondUser.setGender(Gender.FEMALE);
		
		User thirdUser = new User();
		thirdUser.setMobileNo("3");
		thirdUser.setAddedBy("1");
		thirdUser.setGender(Gender.FEMALE);
		
		Event event = new Event();
		event.setAmountSpent(BigDecimal.valueOf(100));
		event.setCategory(CategoryType.FOOD);
		event.setUser(primaryUser);
		
		EventVO eventRequest = new EventVO();
		eventRequest.setEvent(event);
		TransactionVO r1 = new TransactionVO();
		r1.setFrom(primaryUser);
		r1.setTo(primaryUser);
		r1.setAmount(BigDecimal.valueOf(33.33f));
		eventRequest.getTransacionVOs().add(r1);
		
		TransactionVO r2 = new TransactionVO();
		r2.setFrom(secondUser);
		r2.setTo(primaryUser);
		r2.setAmount(BigDecimal.valueOf(33.33f));
		eventRequest.getTransacionVOs().add(r2);
		
		TransactionVO r3 = new TransactionVO();
		r3.setFrom(thirdUser);
		r3.setTo(primaryUser);
		r3.setAmount(BigDecimal.valueOf(33.33f));
		eventRequest.getTransacionVOs().add(r3);
		
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
	public void testGetEventsByMobileNumber() {
		User user1 = new User();
		user1.setName("p1");
		user1.setMobileNo("1");
		user1.setAddedBy("1");
		user1.setGender(Gender.FEMALE);
		
		User user2 = new User();
		user2.setMobileNo("2");
		user2.setAddedBy("1");
		user2.setGender(Gender.FEMALE);
		
		User user3 = new User();
		user3.setMobileNo("3");
		user3.setAddedBy("1");
		user3.setGender(Gender.FEMALE);
		
		
		Event event1 = new Event();
		event1.setAmountSpent(BigDecimal.valueOf(100));
		event1.setCategory(CategoryType.FOOD);
		event1.setUser(user1);
		
		Event savedEvent1 = eventRepository.save(event1);
		
		Transaction t1 = new Transaction();
		t1.setAmount(BigDecimal.valueOf(50));
		t1.setFrom(user1);
		t1.setTo(user1);
		t1.setEvent(savedEvent1);
		transactionRepository.save(t1);
		
		Transaction t2 = new Transaction();
		t2.setAmount(BigDecimal.valueOf(50));
		t2.setFrom(user2);
		t2.setTo(user1);
		t2.setEvent(savedEvent1);
		transactionRepository.save(t2);
		
		Event event2 = new Event();
		event2.setAmountSpent(BigDecimal.valueOf(100));
		event2.setCategory(CategoryType.TRAVEL);
		event2.setUser(user3);
		
		Event savedEvent2 = eventRepository.save(event2);
		
		Transaction t3 = new Transaction();
		t3.setAmount(BigDecimal.valueOf(50));
		t3.setFrom(user3);
		t3.setTo(user3);
		t3.setEvent(savedEvent2);
		transactionRepository.save(t3);
		
		Transaction t4 = new Transaction();
		t4.setAmount(BigDecimal.valueOf(50));
		t4.setFrom(user1);
		t4.setTo(user3);
		t4.setEvent(savedEvent2);
		transactionRepository.save(t4);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-auth-token", getToken());
		HttpEntity getRoomMateEntity = new HttpEntity(headers);
		final ResponseEntity<List<User>> responseGet = this.restTemplate.exchange(getBaseUrl() + "/api/event/{mobileNo}",
				HttpMethod.GET, getRoomMateEntity, new ParameterizedTypeReference<List<User>>() {
				},
				user1.getMobileNo());
		assertTrue(responseGet.getStatusCode().equals(HttpStatus.OK));
		assertTrue(responseGet.getBody().size() == 1);
	}
}