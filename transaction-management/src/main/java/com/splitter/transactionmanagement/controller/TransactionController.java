package com.splitter.transactionmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.splitter.transactionmanagement.controller.dto.TransactionVO;
import com.splitter.transactionmanagement.service.TransactionService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
	
	private final TransactionService transactionService;
	
	@Autowired
	public TransactionController(TransactionService transactionService) {
		super();
		this.transactionService = transactionService;
	}

    
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<TransactionVO>> getTransactions(@RequestParam(required = false) final String owedBy,
			@RequestParam(required = false) final String owedTo){
		// TODO move to validation filter
		if(owedBy == null && owedTo == null) {
			throw new RuntimeException("bad request");
		}
		return new ResponseEntity<>(transactionService.getTransactions(owedBy, owedTo), HttpStatus.OK);
	}

}
