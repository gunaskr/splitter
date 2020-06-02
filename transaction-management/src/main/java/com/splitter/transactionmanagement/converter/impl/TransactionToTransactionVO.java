package com.splitter.transactionmanagement.converter.impl;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.splitter.transactionmanagement.controller.dto.TransactionVO;
import com.splitter.transactionmanagement.model.Transaction;

@Component
public class TransactionToTransactionVO implements Converter<Transaction, TransactionVO> {

	@Override
	public TransactionVO convert(final Transaction source) {
		final TransactionVO transactionVO = new TransactionVO();
		transactionVO.setAmount(source.getAmount());
		transactionVO.setFromUserId(source.getFromUserId());
		transactionVO.setToUserId(source.getToUserId());
		return transactionVO;
	}

}
