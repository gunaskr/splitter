package com.splitter.transactionmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.splitter.transactionmanagement.controller.dto.TransactionVO;
import com.splitter.transactionmanagement.service.TransactionService;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
	
	private final TransactionService transactionService;
	
	@Autowired
	public TransactionController(TransactionService transactionService) {
		super();
		this.transactionService = transactionService;
	}

    
	@GetMapping
	public ResponseEntity<List<TransactionVO>> getTransactions(@RequestParam(required = false) final String owedBy,
			@RequestParam(required = false) final String owedTo){
		if(owedBy == null && owedTo == null) {
			throw new IllegalArgumentException("bad request either owedBy or owedTo must be passed");
		}
		return new ResponseEntity<>(transactionService.getTransactions(owedBy, owedTo), HttpStatus.OK);
	}
	
	@DeleteMapping(path="/{transactionId}")
	public void deleteTransactionById(@PathVariable final String transactionId) {
		transactionService.deleteTransactionById(transactionId);
	}

}
