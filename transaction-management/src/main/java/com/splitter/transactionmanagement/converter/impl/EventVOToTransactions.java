package com.splitter.transactionmanagement.converter.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.splitter.transactionmanagement.controller.dto.EventVO;
import com.splitter.transactionmanagement.controller.dto.TransactionVO;
import com.splitter.transactionmanagement.model.Transaction;

@Component
public class EventVOToTransactions implements Converter<EventVO, List<Transaction>> {

	@Override
	public List<Transaction> convert(EventVO source) {
		final TransactionVOToTransaction transactionRequestToTransaction = new TransactionVOToTransaction(source.getEvent());
		final List<Transaction> transactions = new ArrayList<>();
		for(final TransactionVO transactionRequest: source.getTransacionVOs()) {
			transactions.add(transactionRequestToTransaction.convert(transactionRequest));
		}
		return transactions;
	}
	
}
