package com.splitter.transactionmanagement.controller.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.splitter.transactionmanagement.model.CategoryType;

public class EventVO {
	private String id;
	private CategoryType category;
	private String name;
	private String userId;
	private BigDecimal amountSpent;
	private List<TransactionVO> transactions = new ArrayList<>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public CategoryType getCategory() {
		return category;
	}

	public void setCategory(CategoryType category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getAmountSpent() {
		return amountSpent;
	}

	public void setAmountSpent(BigDecimal amountSpent) {
		this.amountSpent = amountSpent;
	}

	public List<TransactionVO> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionVO> transactions) {
		this.transactions = transactions;
	}
}
