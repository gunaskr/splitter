package com.splitter.user.dto;

import java.io.Serializable;

import com.splitter.user.model.Gender;

public class RoomMateDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -863626026178247240L;
	private String username;
	private String mobileNo;
	private String addedBy;
	private Gender gender;

	public RoomMateDTO() {
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

}
