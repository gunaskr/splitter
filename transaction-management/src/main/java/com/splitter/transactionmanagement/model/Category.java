package com.splitter.transactionmanagement.model;

public class Category extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private CategoryType type;
	private String icon;
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public CategoryType getType() {
		return type;
	}
	public void setType(CategoryType type) {
		this.type = type;
	}
}
