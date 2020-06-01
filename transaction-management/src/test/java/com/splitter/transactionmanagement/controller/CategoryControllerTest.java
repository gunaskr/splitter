package com.splitter.transactionmanagement.controller;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.splitter.transactionmanagement.AbstractIntegrationTest;
import com.splitter.transactionmanagement.model.Category;

public class CategoryControllerTest extends AbstractIntegrationTest {
	
	@Before
    public void beforeTest() {
		// do nothing
    }

	@Test
	public void testFindAll() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-auth-token", getToken());
		HttpEntity getRoomMateEntity = new HttpEntity(headers);
		final ResponseEntity<List<Category>> getCategoriesResponse = this.restTemplate.exchange(getBaseUrl() + "/api/category",
				HttpMethod.GET, getRoomMateEntity, new ParameterizedTypeReference<List<Category>>() {
				});
		assertTrue(getCategoriesResponse.getStatusCode().equals(HttpStatus.OK));
		assertTrue(getCategoriesResponse.getBody().size() == 2);
	}
}