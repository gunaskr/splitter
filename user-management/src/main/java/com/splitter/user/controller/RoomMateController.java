package com.splitter.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitter.user.converter.dto.UserDTOToUser;
import com.splitter.user.dto.UserDTO;
import com.splitter.user.model.User;
import com.splitter.user.service.UserService;

@RestController
@RequestMapping("/api/v1/roommate")
public class RoomMateController {
	
	private final UserService userService;
	private final UserDTOToUser roomMateDTOToUserConverter;
	
	@Autowired
	public RoomMateController(final UserService service,
			@Qualifier("roomMateConverter") final UserDTOToUser roomMateDTOToUserConverter) {
		this.userService = service;
		this.roomMateDTOToUserConverter = roomMateDTOToUserConverter;
	}
	
	@PostMapping
    public ResponseEntity<List<User>> createRoomMates(@RequestBody final List<UserDTO> roomMates) {
		final List<User> users = roomMates.stream().map(roomMateDTOToUserConverter::convert).collect(Collectors.toList());
        return new ResponseEntity<>(userService.createRoomMates(users), HttpStatus.OK);
    }
	
	@GetMapping(path="/{mobileNo}")
    public ResponseEntity<List<User>> getRoomMates(@PathVariable final String mobileNo) {
		final List<User> roomMatesByMobileNo = userService.getRoomMatesByMobileNo(mobileNo);
        return new ResponseEntity<>(roomMatesByMobileNo, HttpStatus.OK);
    }
	
	@DeleteMapping(path="/{mobileNo}/{addedBy}")
    public void deleteRoomMate(@PathVariable final String mobileNo, @PathVariable final String addedBy) {
		userService.deleteRoomMate(mobileNo, addedBy);
    }
}
