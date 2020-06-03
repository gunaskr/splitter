package com.splitter.transactionmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.splitter.transactionmanagement.controller.dto.EventVO;
import com.splitter.transactionmanagement.service.EventService;

@RestController
@RequestMapping("/api/event")
public class EventController {
	
	
	private final EventService eventService;
	
	@Autowired
	public EventController(final EventService eventService) {
		super();
		this.eventService = eventService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<EventVO> createEvent(@RequestBody EventVO eventRequest) {
		return new ResponseEntity<>(eventService.createEvent(eventRequest), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EventVO>> filterEvents(@RequestParam(required = false) final String fromUserId,
			@RequestParam(required = false) final String toUserId) {
		if(fromUserId == null && toUserId == null) {
			throw new RuntimeException("bad request");
		}
		return new ResponseEntity<>(eventService.filterEvents(fromUserId, toUserId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/{eventId}")
	public ResponseEntity<EventVO> findEventById(@PathVariable final String eventId) {
		return new ResponseEntity<>(eventService.findEventById(eventId), HttpStatus.OK);
	}

}
