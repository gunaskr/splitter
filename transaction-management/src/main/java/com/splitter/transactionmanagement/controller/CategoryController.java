package com.splitter.transactionmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.splitter.transactionmanagement.model.Category;
import com.splitter.transactionmanagement.repository.CategoryRepository;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	private final CategoryRepository categoryRepository;
	
	@Autowired
	public CategoryController(CategoryRepository categoryRepository) {
		super();
		this.categoryRepository = categoryRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Category>> findAll(){
		return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
	}
}
