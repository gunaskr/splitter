package com.splitter.transactionmanagement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.splitter.transactionmanagement.model.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {

}
