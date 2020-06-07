package com.splitter.user.converter.dto;

import java.util.Collections;

import org.springframework.core.convert.converter.Converter;

import com.splitter.user.dto.RoomMateDTO;
import com.splitter.user.model.User;
import com.splitter.user.model.User.CompositeKey;

public class RoomMateDTOToUserConverter implements Converter<RoomMateDTO, User> {

	@Override
	public User convert(RoomMateDTO source) {
		final User user = new User();
		CompositeKey key = new CompositeKey();
		key.setMobileNo(source.getMobileNo());
		key.setAddedBy(source.getAddedBy());
		user.setCompositeKey(key);
		user.setGender(source.getGender());
		user.setUsername(source.getUsername());
		user.setAccountNonExpired(false);
        user.setCredentialsNonExpired(false);
        user.setEnabled(true);
        /* room mates have no authority to access any end-points */
        user.setAuthorities(Collections.emptyList());
		return user;
	}
	
}
