package com.splitter.transactionmanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.splitter.security.config.TokenHolder;
import com.splitter.transactionmanagement.controller.dto.TransactionVO;
import com.splitter.transactionmanagement.converter.impl.TransactionToTransactionVO;
import com.splitter.transactionmanagement.model.Transaction;
import com.splitter.transactionmanagement.model.User;
import com.splitter.transactionmanagement.repository.TransactionRepository;
import com.splitter.transactionmanagement.repository.UserManagementClient;

@Service
public class TransactionService {
	
	private final TransactionRepository transactionRepository;
	private final UserManagementClient userManagementClient;
	private final TokenHolder tokenHolder;
	
	@Autowired
	public TransactionService(final TransactionRepository transactionRepository,
			final UserManagementClient userManagementClient,
			final TokenHolder tokenHolder) {
		super();
		this.transactionRepository = transactionRepository;
		this.userManagementClient = userManagementClient;
		this.tokenHolder = tokenHolder;
	}


	public List<TransactionVO> getTransactions(final String owedBy, final String owedTo){
		final List<Transaction> transactions = transactionRepository.findByFromUserIdOrToUserId(owedBy, owedTo);
		final List<User> users = userManagementClient.getRoomMates(
				 ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails()).getUsername()
				, tokenHolder.getToken());
		final TransactionToTransactionVO transactionToTransactionVO = new TransactionToTransactionVO(users);
		return transactions.stream().map(transaction -> transactionToTransactionVO.convert(transaction)).collect(Collectors.toList());
	}

	public void deleteTransactionById(String transactionId) {
		transactionRepository.deleteById(transactionId);
	}
}
