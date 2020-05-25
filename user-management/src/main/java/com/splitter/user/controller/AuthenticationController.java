package com.splitter.user.controller;

import com.splitter.security.service.TokenService;
import com.splitter.user.dto.LoginDTO;
import com.splitter.user.dto.TokenDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final TokenService tokenService;
    
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthenticationController(final TokenService tokenService, final UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody final LoginDTO dto) {
    	final UserDetails loadUserByUsername = userDetailsService.loadUserByUsername(dto.getUsername());
    	if(loadUserByUsername.getPassword().equals(dto.getPassword())) {
    		final String token = tokenService.getToken(loadUserByUsername);
            final TokenDTO response = new TokenDTO();
            response.setToken(token);
            return new ResponseEntity<>(response, HttpStatus.OK);
    	} else {
            return new ResponseEntity<>("Authentication failed", HttpStatus.BAD_REQUEST);
        }
    }
}
