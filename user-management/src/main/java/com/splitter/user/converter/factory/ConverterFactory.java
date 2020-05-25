package com.splitter.user.converter.factory;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.splitter.user.converter.dto.RoomMateDTOToUserConverter;
import com.splitter.user.converter.dto.UserDTOConverter;
import com.splitter.user.dto.RoomMateDTO;
import com.splitter.user.dto.UserDTO;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@Component
public class ConverterFactory {

    private Map<Object, Converter<?,?>> converters;

    public ConverterFactory() {

    }

    @PostConstruct
    public void init() {
        converters = new HashMap<>();
        converters.put(UserDTO.class, new UserDTOConverter());
        converters.put(RoomMateDTO.class, new RoomMateDTOToUserConverter());
    }

    public Converter getConverter(final Object type) {
        return converters.get(type);
    }
}
