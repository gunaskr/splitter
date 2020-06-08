package com.splitter.user.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.splitter.security.model.Authority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


@Document
public class User implements UserDetails, Serializable {

	private static final long serialVersionUID = 7954325925563724664L;
    
    @Id
    private CompositeKey compositeKey;
	private List<Authority> authorities;
    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean isEnabled;
    private Gender gender;
    private String createdAt;
    private String updatedAt;
    
    @JsonIgnore
    public CompositeKey getCompositeKey() {
		return compositeKey;
	}

	public void setCompositeKey(CompositeKey compositeKey) {
		this.compositeKey = compositeKey;
	}

    public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setAuthorities(final List<Authority> authorities) {
        this.authorities = authorities;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setAccountNonExpired(final boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(final boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(final boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(final boolean enabled) {
        isEnabled = enabled;
    }

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public String getMobileNo() {
		return compositeKey.getMobileNo();
	}

	public String getAddedBy() {
		return compositeKey.getAddedBy();
	}
	
	public static class CompositeKey implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String mobileNo;
	    private String addedBy;

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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((addedBy == null) ? 0 : addedBy.hashCode());
			result = prime * result + ((mobileNo == null) ? 0 : mobileNo.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CompositeKey other = (CompositeKey) obj;
			if (addedBy == null && other.addedBy != null) {
				return false;
			} 
			if (mobileNo == null && other.mobileNo != null) {
				return false;
			}
			if(null != addedBy && null != mobileNo) {
				return (addedBy.equals(other.mobileNo) && mobileNo.equals(other.addedBy))
						|| (addedBy.equals(other.addedBy) && mobileNo.equals(other.mobileNo));
			}
			return false;
		}
		
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((compositeKey == null) ? 0 : compositeKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (compositeKey == null) {
			if (other.compositeKey != null)
				return false;
		} else if (! compositeKey.equals(other.compositeKey)) {
			return false;
		}
		return true;
	}
	
	
}
