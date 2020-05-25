package com.splitter.user.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.splitter.user.converter.factory.ConverterFactory;
import com.splitter.user.dto.RoomMateDTO;
import com.splitter.user.dto.UserDTO;
import com.splitter.user.model.User;


@Component
public class ConverterFacade {

    @Autowired
    private ConverterFactory converterFactory;

    public User convert(final UserDTO dto) {
        return (User) converterFactory.getConverter(dto.getClass()).convert(dto);
    }
    
    public User convert(final RoomMateDTO dto) {
    	return (User) converterFactory.getConverter(dto.getClass()).convert(dto);
    }
}
