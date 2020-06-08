package com.splitter.user.service;

import java.util.List;

import com.splitter.user.model.User;


public interface UserService {

    User create(User object);

    User findByUsername(String userName);

    List<User> findAll();
    
    User findUserByMobileNoAndAddedBy(final String mobileNo, final String addedBy);
    
    List<User> createRoomMates(List<User> users);
    
    List<User> getRoomMatesByMobileNo(String mobileNo);

	void deleteRoomMate(String mobileNo, String addedBy);
}
