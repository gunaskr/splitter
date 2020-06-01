package com.splitter.transactionmanagement.model;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Transaction extends BaseEntity {
	private static final long serialVersionUID = 8626899360367248309L;
	
	private User from;
	private User to;
	private BigDecimal amount;
	
	@DBRef
	private Event event;
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
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
