package com.splitter.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitter.security.service.TokenService;
import com.splitter.user.dto.LoginDTO;
import com.splitter.user.dto.TokenDTO;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final TokenService tokenService;
    
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthenticationController(final TokenService tokenService, final UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<TokenDTO> authenticate(@RequestBody final LoginDTO dto) {
    	final UserDetails loadUserByUsername = userDetailsService.loadUserByUsername(dto.getUsername());
    	
    	if( ! BCrypt.checkpw(dto.getPassword(), loadUserByUsername.getPassword())) {
    		throw new BadCredentialsException("wrong password");
    	}
    	
    	final String token = tokenService.getToken(loadUserByUsername);
        final TokenDTO response = new TokenDTO();
        response.setToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
