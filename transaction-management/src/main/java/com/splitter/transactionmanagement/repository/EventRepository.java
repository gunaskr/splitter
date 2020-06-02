package com.splitter.transactionmanagement.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.splitter.transactionmanagement.model.Event;

public interface EventRepository extends MongoRepository<Event, String> {

	List<Event> findByUserId(String mobileNo);

}
