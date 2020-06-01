package com.splitter.transactionmanagement.converter.impl;

import org.springframework.core.convert.converter.Converter;

import com.splitter.transactionmanagement.controller.dto.TransactionVO;
import com.splitter.transactionmanagement.model.Event;
import com.splitter.transactionmanagement.model.Transaction;

public class TransactionVOToTransaction implements Converter<TransactionVO, Transaction> {
	
	private final Event event;
	
	public TransactionVOToTransaction(Event event) {
		super();
		this.event = event;
	}

	@Override
	public Transaction convert(TransactionVO source) {
		final Transaction transaction = new Transaction();
		transaction.setEvent(event);
		transaction.setAmount(source.getAmount());
		transaction.setFrom(source.getFrom());
		transaction.setTo(source.getTo());
		return transaction;
	}

}
