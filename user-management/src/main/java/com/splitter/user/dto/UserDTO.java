package com.splitter.user.dto;

import java.io.Serializable;

import com.splitter.user.model.Gender;


public class UserDTO implements Serializable {

    private static final long serialVersionUID = 91901774547107674L;

    private String username;
    private String password;
    private String mobileNo;
    private String addedBy;
    private Gender gender;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
}
