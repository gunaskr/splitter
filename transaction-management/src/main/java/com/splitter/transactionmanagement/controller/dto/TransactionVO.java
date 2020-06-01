package com.splitter.transactionmanagement.controller.dto;

import java.math.BigDecimal;

import com.splitter.transactionmanagement.model.User;

public class TransactionVO {
	private User from;
	private User to;
	private BigDecimal amount;
	public User getFrom() {
		return from;
	}
	public void setFrom(User from) {
		this.from = from;
	}
	public User getTo() {
		return to;
	}
	public void setTo(User to) {
		this.to = to;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
