package com.splitter.transactionmanagement.controller.dto;

import java.math.BigDecimal;

import com.splitter.transactionmanagement.model.User;

public class TransactionVO {
	private String id;
	private User fromUser;
	private User toUser;
	private BigDecimal amount;
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getFromUser() {
		return fromUser;
	}
	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	public User getToUser() {
		return toUser;
	}
	public void setToUser(User toUser) {
		this.toUser = toUser;
	}
	
}
