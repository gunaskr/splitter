package com.splitter.user.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.splitter.user.model.User;
import com.splitter.user.repository.UserRepository;


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
    	return repository.findByCompositeKeyMobileNoAndCompositeKeyAddedBy(mobileNo, addedBy);
    }

	@Override
	public List<User> createRoomMates(List<User> users) {
		final String mobileNo = SecurityContextHolder.getContext().getAuthentication().getName();
		final List<User> roomMatesBySelf = repository.findByCompositeKeyAddedByAndCompositeKeyMobileNoNot(mobileNo, mobileNo);
		final List<User> roomMatesAddedByOthers = repository.findByCompositeKeyMobileNoAndCompositeKeyAddedByNot(mobileNo, mobileNo);
		if((roomMatesBySelf.size() + roomMatesAddedByOthers.size() + users.size()) > 6) {
			throw new RuntimeException("cannot add more than 6 room mates for one user" + mobileNo);
		}
		for(final User newUser: users) {
			if(roomMatesAddedByOthers.contains(newUser)) {
				throw new RuntimeException("cannot add room mate" + " mobileNo " + newUser.getCompositeKey().getMobileNo() + " addedBy " 
			+ newUser.getCompositeKey().getAddedBy() + "for user " + mobileNo);
			}
		}
		
		return repository.saveAll(users);
	}
	
	/**
	 * Gets room mates along with user details.
	 * @param mobileNo of the user
	 * @return list of room mates{@link User}
	 */
	@Override
	public List<User> getRoomMatesByMobileNo(String mobileNo) {
		/* finding all the users added by current user */
		final List<User> roomMates = repository.findByCompositeKeyAddedBy(mobileNo);
		/* finding all the users who added current user as room mate */
		final List<User> roomMatesWhoAddedCurrentUserAsRommmate = repository.findByCompositeKeyMobileNoAndCompositeKeyAddedByNot(mobileNo, mobileNo);
		for(final User otherUser: roomMatesWhoAddedCurrentUserAsRommmate) {
			roomMates.add(repository.findByCompositeKeyMobileNoAndCompositeKeyAddedBy(otherUser.getCompositeKey().getAddedBy(), otherUser.getCompositeKey().getAddedBy()));
		}
		
		return roomMates;
	}

	@Override
	public void deleteRoomMate(String mobileNo, String addedBy) {
		repository.deleteByCompositeKeyMobileNoAndCompositeKeyAddedBy(mobileNo, addedBy);
	}
}
