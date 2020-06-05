package com.splitter.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.splitter.user.model.User;
import com.splitter.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class BasicUserService implements UserService {

    private final UserRepository repository;

    @Autowired
    public BasicUserService(final UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(final User user) {
        user.setCreatedAt(String.valueOf(LocalDateTime.now()));
        return repository.save(user);
    }

    @Override
    public User find(final String id) {
    	Optional<User> findById = repository.findById(id);
    	if(findById.isPresent()){
    		return findById.get();
    	}
        return null;
    }

    @Override
    public User findByUsername(final String userName) {
        return repository.findByUsername(userName);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
    
    @Override
    public User findUserByMobileNoAndAddedBy(final String mobileNo, final String addedBy) {
    	return repository.findByMobileNoAndAddedBy(mobileNo, addedBy);
    }

	@Override
	public List<User> createRoomMates(List<User> users) {
		return repository.saveAll(users);
	}

	@Override
	public List<User> getRoomMatesByMobileNo(String mobileNo) {
		/* finding all the users added by current user */
		final List<User> roomMates = repository.findByAddedBy(mobileNo);
		/* finding all the users who added current user as room mate */
		final List<User> roomMatesWhoAddedCurrentUserAsRommmate = repository.findByMobileNoAndAddedByNot(mobileNo, mobileNo);
		for(final User otherUser: roomMatesWhoAddedCurrentUserAsRommmate) {
			roomMates.add(repository.findByMobileNoAndAddedBy(otherUser.getAddedBy(), otherUser.getAddedBy()));
		}
		
		return roomMates;
	}

	@Override
	public void deleteRoomMate(String mobileNo, String addedBy) {
		repository.deleteByMobileNoAndAddedBy(mobileNo, addedBy);
	}
}
