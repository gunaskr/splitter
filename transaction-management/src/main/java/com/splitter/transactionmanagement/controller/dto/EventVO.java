package com.splitter.transactionmanagement.controller.dto;

import java.util.ArrayList;
import java.util.List;

import com.splitter.transactionmanagement.model.Event;

public class EventVO {
	private Event event;
	private List<TransactionVO> transactions = new ArrayList<>();
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public List<TransactionVO> getTransacionVOs() {
		return transactions;
	}
	public void setTransactionRequests(List<TransactionVO> transactions) {
		this.transactions = transactions;
	}
	
}
