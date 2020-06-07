package com.splitter.user.converter.dto;

import com.splitter.security.model.Authority;
import com.splitter.user.dto.UserDTO;
import com.splitter.user.model.User;
import com.splitter.user.model.User.CompositeKey;

import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;


public class UserDTOConverter implements Converter<UserDTO, User> {

    @Override
    public User convert(final UserDTO dto) {
        final User user = new User();

        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setAccountNonExpired(false);
        user.setCredentialsNonExpired(false);
        user.setEnabled(true);

        List<Authority> authorities = new ArrayList<>();
        authorities.add(Authority.ROLE_USER);
        user.setAuthorities(authorities);
        
        CompositeKey key = new CompositeKey();
		key.setMobileNo(dto.getMobileNo());
		key.setAddedBy(dto.getAddedBy());
		user.setCompositeKey(key);
        user.setGender(dto.getGender());
        return user;
    }
}
