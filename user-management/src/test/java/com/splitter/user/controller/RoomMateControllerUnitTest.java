package com.splitter.user.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.splitter.user.dto.RoomMateDTO;
import com.splitter.user.model.Gender;
import com.splitter.user.service.UserService;

//TODO : remove if there is no unit testing needed.
@WebMvcTest(RoomMateController.class)
@TestPropertySource(locations="classpath:application-test.properties")
public class RoomMateControllerUnitTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	private static ObjectMapper objectMapper = new ObjectMapper();

	@Ignore
	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		List<RoomMateDTO> users = new ArrayList<>();
		RoomMateDTO user = new RoomMateDTO();
		user.setMobileNo("1");
		user.setAddedBy("2");
		user.setGender(Gender.FEMALE);
		user.setUsername("test");
		users.add(user);
		
		this.mockMvc.perform(
				MockMvcRequestBuilders.post("/api/roommate")
				.content(objectMapper.writeValueAsString(users))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
