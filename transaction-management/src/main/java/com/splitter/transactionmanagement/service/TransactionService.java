package com.splitter.transactionmanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.splitter.transactionmanagement.controller.dto.TransactionVO;
import com.splitter.transactionmanagement.converter.impl.TransactionToTransactionVO;
import com.splitter.transactionmanagement.model.Transaction;
import com.splitter.transactionmanagement.repository.TransactionRepository;

@Service
public class TransactionService {
	
	private final TransactionRepository transactionRepository;
	private final TransactionToTransactionVO transactionToTransactionVO;
	
	@Autowired
	public TransactionService(final TransactionRepository transactionRepository,
			final TransactionToTransactionVO transactionToTransactionVO) {
		super();
		this.transactionRepository = transactionRepository;
		this.transactionToTransactionVO = transactionToTransactionVO;
	}


	public List<TransactionVO> getTransactions(final String owedBy, final String owedTo){
		final List<Transaction> transactions = transactionRepository.findByFromUserIdOrToUserId(owedBy, owedTo);
		
		return transactions.stream().map(transaction -> transactionToTransactionVO.convert(transaction)).collect(Collectors.toList());
	}

}
