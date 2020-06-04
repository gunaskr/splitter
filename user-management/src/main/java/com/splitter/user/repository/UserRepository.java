package com.splitter.user.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.splitter.user.model.User;


@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(final String userName);
    
    User findByMobileNoAndAddedBy(final String mobileNo, final String addedBy);
    
    List<User> findByAddedByAndMobileNoNot(final String addedBy, final String mobileNo);
    
    List<User> findByMobileNoAndAddedByNot(final String mobileNo, final String addedBy);
    
    void deleteByMobileNoAndAddedBy(final String mobileNo, final String addedBy);
}
