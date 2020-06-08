package com.splitter.transactionmanagement.converter.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;

import com.splitter.transactionmanagement.controller.dto.TransactionVO;
import com.splitter.transactionmanagement.model.Transaction;
import com.splitter.transactionmanagement.model.User;

public class TransactionToTransactionVO implements Converter<Transaction, TransactionVO> {
	
	private final Map<String, User> userMap;

	public TransactionToTransactionVO(List<User> users) {
		super();
		this.userMap = users.stream().collect(Collectors.toMap(User::getMobileNo, Function.identity()));
	}

	@Override
	public TransactionVO convert(final Transaction source) {
		final TransactionVO transactionVO = new TransactionVO();
		transactionVO.setAmount(source.getAmount());
		User user = userMap.get(source.getFromUserId());
		if(user == null) {
			user = new User();
			user.setMobileNo(source.getFromUserId());
		}
		transactionVO.setFromUser(user);
		transactionVO.setToUser(userMap.get(source.getToUserId()));
		return transactionVO;
	}

}
