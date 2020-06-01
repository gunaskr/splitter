package com.splitter.transactionmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.splitter.transactionmanagement.controller.dto.EventVO;
import com.splitter.transactionmanagement.converter.impl.EventVOToTransactions;
import com.splitter.transactionmanagement.service.EventService;

@RestController
@RequestMapping("/api/event")
public class EventController {
	
	
	private final EventService eventService;
	
	@Autowired
	public EventController(EventVOToTransactions eventVOToTransactions,
			EventService eventService) {
		super();
		this.eventService = eventService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<EventVO> createEvent(@RequestBody EventVO eventRequest) {
		return new ResponseEntity<>(eventService.createEvent(eventRequest), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/{mobileNo}")
	public ResponseEntity<List<EventVO>> getAllEventsByUser(@PathVariable final String mobileNo) {
		return new ResponseEntity<>(eventService.getEventsByMobileNo(mobileNo), HttpStatus.OK);
	}

}
