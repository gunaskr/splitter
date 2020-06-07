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
    public UserDetails loadUserByUsername(final String mobileNo) throws UsernameNotFoundException {
        final User user = userService.findUserByMobileNoAndAddedBy(mobileNo, mobileNo);
        /* we want to capture mobileNo as user name */
        user.setUsername(user.getCompositeKey().getMobileNo());
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("User with username:" + mobileNo + " not found");
        }
    }
}
