package com.splitter.transactionmanagement.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.splitter.transactionmanagement.model.CategoryType;
import com.splitter.transactionmanagement.model.User;

public class EventVO {
	private String id;
	private CategoryType category;
	private String name;
	private User user;
	private BigDecimal amountSpent;
	private LocalDateTime createdAt;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
