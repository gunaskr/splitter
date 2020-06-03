package com.splitter.transactionmanagement.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.splitter.transactionmanagement.model.Transaction;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
	
	@Query("{'event.$id' : {$in: ?0 }}")
	List<Transaction> findByEventIn(List<ObjectId> collect);

	List<Transaction> findByFromUserIdOrToUserId(String owedBy, String owedTo);
	
	@Query("{'event.$id' : ?0 }")
	List<Transaction> findByEventId(ObjectId eventId);

}
