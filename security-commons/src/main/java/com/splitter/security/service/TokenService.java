package com.splitter.security.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {

    String getToken(UserDetails user);
}
