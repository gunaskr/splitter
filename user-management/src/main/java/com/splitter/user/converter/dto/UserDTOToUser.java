package com.splitter.user.converter.dto;

import com.splitter.security.model.Authority;
import com.splitter.user.dto.UserDTO;
import com.splitter.user.model.User;
import com.splitter.user.model.User.CompositeKey;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserDTOToUser implements Converter<UserDTO, User> {
	
	private final boolean isRoomMate;
	
	public UserDTOToUser(final boolean isRoomMate) {
		this.isRoomMate = isRoomMate;
	}
	
	public UserDTOToUser() {
		this.isRoomMate = false;
	}

    @Override
    public User convert(final UserDTO dto) {
        final User user = new User();

        user.setUsername(dto.getUsername());
        user.setAccountNonExpired(false);
        user.setCredentialsNonExpired(false);
        user.setEnabled(true);
        if(! isRoomMate) {
        	user.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
        	List<Authority> authorities = new ArrayList<>();
            authorities.add(Authority.ROLE_USER);
            user.setAuthorities(authorities);
        } else {
        	user.setAuthorities(Collections.emptyList());
        }
        
        CompositeKey key = new CompositeKey();
		key.setMobileNo(dto.getMobileNo());
		key.setAddedBy(dto.getAddedBy());
		user.setCompositeKey(key);
        user.setGender(dto.getGender());
        return user;
    }
}
