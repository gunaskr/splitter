package com.splitter.transactionmanagement.model;

import java.math.BigDecimal;

public class Event extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CategoryType category;
	private String name;
	private User user;
	private BigDecimal amountSpent;
	
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

}
