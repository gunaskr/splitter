package com.splitter.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.splitter.user.converter.ConverterFacade;
import com.splitter.user.dto.RoomMateDTO;
import com.splitter.user.model.User;
import com.splitter.user.service.UserService;

@RestController
@RequestMapping("/api/roommate")
public class RoomMateController {
	
	private final UserService userService;
	
	private final ConverterFacade converterFacade;
	
	@Autowired
	public RoomMateController(final UserService service, final ConverterFacade converterFacade) {
		this.userService = service;
		this.converterFacade = converterFacade;
	}
	
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<List<User>> createRoomMates(@RequestBody final List<RoomMateDTO> roomMates) {
		final List<User> users = roomMates.stream().map(roomMate -> converterFacade.convert(roomMate)).collect(Collectors.toList());
        return new ResponseEntity<>(userService.createRoomMates(users), HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.GET, path="/{mobileNo}")
    public ResponseEntity<List<User>> getRoomMates(@PathVariable final String mobileNo) {
		final List<User> roomMatesByMobileNo = userService.getRoomMatesByMobileNo(mobileNo);
        return new ResponseEntity<>(roomMatesByMobileNo, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.DELETE, path="/{mobileNo}/{addedBy}")
    public void deleteRoomMate(@PathVariable final String mobileNo, @PathVariable final String addedBy) {
		userService.deleteRoomMate(mobileNo, addedBy);
    }
}
