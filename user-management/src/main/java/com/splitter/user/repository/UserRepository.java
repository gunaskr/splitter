package com.splitter.user.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.splitter.user.model.User;
import com.splitter.user.model.User.CompositeKey;


@Repository
public interface UserRepository extends MongoRepository<User, CompositeKey> {

    User findByUsername(final String userName);
    
    List<User> findByCompositeKeyAddedBy(final String addedBy);
    
    List<User> findByCompositeKeyMobileNoAndCompositeKeyAddedByNot(final String mobileNo, final String addedBy);
    
    void deleteByCompositeKeyMobileNoAndCompositeKeyAddedBy(final String mobileNo, final String addedBy);

	List<User> findByCompositeKeyAddedByAndCompositeKeyMobileNoNot(String addedBy, String mobileNo);
}
