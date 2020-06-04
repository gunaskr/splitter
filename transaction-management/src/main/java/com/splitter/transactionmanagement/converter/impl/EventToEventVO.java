package com.splitter.transactionmanagement.converter.impl;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.splitter.transactionmanagement.controller.dto.EventVO;
import com.splitter.transactionmanagement.model.Event;

@Component
public class EventToEventVO implements Converter<Event, EventVO> {

	@Override
	public EventVO convert(Event source) {
		final EventVO target = new EventVO();
		target.setAmountSpent(source.getAmountSpent());
		target.setCategory(source.getCategory());
		target.setUserId(source.getUserId());
		target.setName(source.getName());
		target.setId(source.getId());
		target.setCreatedAt(source.getCreatedAt());
		return target;
	}

}
