package com.splitter.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitter.user.converter.dto.UserDTOToUser;
import com.splitter.user.dto.UserDTO;
import com.splitter.user.model.User;
import com.splitter.user.service.UserService;


@RestController
@RequestMapping("/api/v1/signup")
public class SignUpController {

    private final UserService service;
    private final UserDTOToUser userDTOToUser;


    @Autowired
    public SignUpController(final UserService service,
    		@Qualifier("userConverter") final UserDTOToUser userDTOToUser) {
        this.service = service;
        this.userDTOToUser = userDTOToUser;
    }

    @PostMapping
    public ResponseEntity<User> signUp(@RequestBody final UserDTO dto) {
        return new ResponseEntity<>(service.create(userDTOToUser.convert(dto)), HttpStatus.OK);
    }
}
