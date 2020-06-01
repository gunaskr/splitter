package com.splitter.transactionmanagement.converter.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.splitter.transactionmanagement.controller.dto.EventVO;
import com.splitter.transactionmanagement.model.Transaction;

@Component
public class TransactionsToEventVO implements Converter<List<Transaction>, EventVO>  {
	private final TransactionToTransactionVO transactionToTransactionVO;
	
	@Autowired
	public TransactionsToEventVO(TransactionToTransactionVO transactionToTransactionVO) {
		super();
		this.transactionToTransactionVO = transactionToTransactionVO;
	}


	/**
	 * {@inheritDoc}
	 * All the transactions are assumed to be of same event.
	 */
	@Override
	public EventVO convert(List<Transaction> source) {
		if(source.isEmpty()) {
			return null;
		}
		final EventVO eventVO = new EventVO();
		eventVO.setEvent(source.get(0).getEvent());
		for(final Transaction transaction: source) {
			eventVO.getTransacionVOs().add(transactionToTransactionVO.convert(transaction));
		}
		return eventVO;
	}

}
