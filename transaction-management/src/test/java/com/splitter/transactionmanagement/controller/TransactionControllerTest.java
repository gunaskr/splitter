package com.splitter.transactionmanagement.controller;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.splitter.transactionmanagement.AbstractIntegrationTest;
import com.splitter.transactionmanagement.controller.dto.TransactionVO;
import com.splitter.transactionmanagement.model.CategoryType;
import com.splitter.transactionmanagement.model.Event;
import com.splitter.transactionmanagement.model.Transaction;
import com.splitter.transactionmanagement.repository.EventRepository;
import com.splitter.transactionmanagement.repository.TransactionRepository;

public class TransactionControllerTest extends AbstractIntegrationTest {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private EventRepository eventRepository;

	@Test
	public void testGetTransactions() throws Exception {

		String user1 = "1";
		
		String user2 = "2";
		
		String user3 = "3";
		
		
		Event event1 = new Event();
		event1.setAmountSpent(BigDecimal.valueOf(150));
		event1.setCategory(CategoryType.FOOD);
		event1.setUserId(user1);
		
		Event savedEvent1 = eventRepository.save(event1);
		
		Transaction t1 = new Transaction();
		t1.setAmount(BigDecimal.valueOf(50));
		t1.setFromUserId(user1);
		t1.setToUserId(user1);
		t1.setEvent(savedEvent1);
		
		Transaction t2 = new Transaction();
		t2.setAmount(BigDecimal.valueOf(50));
		t2.setFromUserId(user2);
		t2.setToUserId(user1);
		t2.setEvent(savedEvent1);
		
		Transaction t5 = new Transaction();
		t5.setAmount(BigDecimal.valueOf(50));
		t5.setFromUserId(user3);
		t5.setToUserId(user1);
		t5.setEvent(savedEvent1);
		
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
		
		Transaction t4 = new Transaction();
		t4.setAmount(BigDecimal.valueOf(50));
		t4.setFromUserId(user1);
		t4.setToUserId(user3);
		t4.setEvent(savedEvent2);
		transactionRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t5));
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-auth-token", getToken());
		HttpEntity getRoomMateEntity = new HttpEntity(headers);
		final ResponseEntity<List<TransactionVO>> responseForOwedBy = this.restTemplate.exchange(getBaseUrl() + "/api/transaction?owedBy={owedBy}&owedTo={owedTo}",
				HttpMethod.GET, getRoomMateEntity, new ParameterizedTypeReference<List<TransactionVO>>() {
				},
				user1, null);
		assertEquals("owed transactions did not match", 2, responseForOwedBy.getBody().size());
		
		final ResponseEntity<List<TransactionVO>> responseForOwedTo = this.restTemplate.exchange(getBaseUrl() + "/api/transaction?owedBy={owedBy}&owedTo={owedTo}",
				HttpMethod.GET, getRoomMateEntity, new ParameterizedTypeReference<List<TransactionVO>>() {
				},
				null, user1);
		
		assertEquals("owed to transactions did not match", 3, responseForOwedTo.getBody().size());
	}
}