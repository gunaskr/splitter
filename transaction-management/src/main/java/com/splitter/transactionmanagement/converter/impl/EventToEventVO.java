package com.splitter.transactionmanagement.converter.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;

import com.splitter.transactionmanagement.controller.dto.EventVO;
import com.splitter.transactionmanagement.model.Event;
import com.splitter.transactionmanagement.model.User;

public class EventToEventVO implements Converter<Event, EventVO> {
	
	private final Map<String, User> userMap;

	public EventToEventVO(List<User> users) {
		super();
		this.userMap = users.stream().collect(Collectors.toMap(User::getMobileNo, Function.identity()));
	}

	@Override
	public EventVO convert(Event source) {
		final EventVO target = new EventVO();
		target.setAmountSpent(source.getAmountSpent());
		target.setCategory(source.getCategory());
		User user = userMap.get(source.getUserId());
		if(user == null) {
			user = new User();
			user.setMobileNo(source.getUserId());
		}
		target.setUser(user);
		target.setName(source.getName());
		target.setId(source.getId());
		target.setCreatedAt(source.getCreatedAt());
		return target;
	}

}
