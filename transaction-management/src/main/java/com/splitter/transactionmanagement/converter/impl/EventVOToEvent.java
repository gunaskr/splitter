package com.splitter.transactionmanagement.converter.impl;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.splitter.transactionmanagement.controller.dto.EventVO;
import com.splitter.transactionmanagement.model.Event;

@Component
public class EventVOToEvent implements Converter<EventVO, Event>  {

	@Override
	public Event convert(EventVO source) {
		final Event target = new Event();
		target.setAmountSpent(source.getAmountSpent());
		target.setCategory(source.getCategory());
		target.setUserId(source.getUserId());
		target.setName(source.getName());
		return target;
	}

}
