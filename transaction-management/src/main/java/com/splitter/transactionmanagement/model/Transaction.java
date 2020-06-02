package com.splitter.transactionmanagement.model;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Transaction extends BaseEntity {
	private static final long serialVersionUID = 8626899360367248309L;
	
	private String fromUserId;
	private String toUserId;
	private BigDecimal amount;
	
	@DBRef
	private Event event;
	
	public String getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getToUserId() {
		return toUserId;
	}
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
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
