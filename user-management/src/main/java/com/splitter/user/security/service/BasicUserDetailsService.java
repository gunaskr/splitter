package com.splitter.user.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.splitter.user.model.User;
import com.splitter.user.service.UserService;


@Service
public class BasicUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public BasicUserDetailsService(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(final String mobileNo) {
        final User user = userService.findUserByMobileNoAndAddedBy(mobileNo, mobileNo);
        if (user != null) {
        	/* we want to capture mobileNo as user name */
            user.setUsername(user.getCompositeKey().getMobileNo());
            return user;
        } else {
            throw new UsernameNotFoundException("User with username:" + mobileNo + " not found");
        }
    }
}
