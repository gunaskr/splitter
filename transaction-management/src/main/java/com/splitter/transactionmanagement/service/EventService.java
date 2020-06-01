package com.splitter.transactionmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.splitter.transactionmanagement.controller.dto.EventVO;
import com.splitter.transactionmanagement.converter.impl.EventVOToTransactions;
import com.splitter.transactionmanagement.converter.impl.TransactionsToEventVO;
import com.splitter.transactionmanagement.model.Event;
import com.splitter.transactionmanagement.model.Transaction;
import com.splitter.transactionmanagement.repository.EventRepository;
import com.splitter.transactionmanagement.repository.TransactionRepository;

@Service
public class EventService {
	
	private final TransactionRepository transactionRepository;
	private final EventVOToTransactions eventVOToTransactions;
	private final TransactionsToEventVO transactionsToEventVO;
	private final EventRepository eventRepository;
	
	@Autowired
	public EventService(final TransactionRepository transactionRepository,
			final EventVOToTransactions eventVOToTransactions,
			final TransactionsToEventVO transactionsToEventVO,
			final EventRepository eventRepository) {
		super();
		this.transactionRepository = transactionRepository;
		this.eventVOToTransactions = eventVOToTransactions;
		this.transactionsToEventVO = transactionsToEventVO;
		this.eventRepository = eventRepository;
	}
	
	//TODO works when we use replica set @Transactional
	public EventVO createEvent(final EventVO eventRequest) {
		final Event savedEvent = eventRepository.save(eventRequest.getEvent());
		eventRequest.setEvent(savedEvent);
		final List<Transaction> transactions = eventVOToTransactions.convert(eventRequest);
		List<Transaction> savedTransactions = transactionRepository.saveAll(transactions);
		return transactionsToEventVO.convert(savedTransactions);
	}

	public List<EventVO> getEventsByMobileNo(String mobileNo) {
		List<Event> events = eventRepository.findByUserMobileNo(mobileNo);
		
		List<ObjectId> collect = events.stream().map((e) -> {
			return new ObjectId(e.getId());
		} ).collect(Collectors.toList());
		List<Transaction> transactions = transactionRepository.findByEventIn(collect);
		Map<String, List<Transaction>> transactionsByEventId = transactions.stream().collect(Collectors.groupingBy((t) -> {
		 return t.getEvent().getId();
		}));
		final List<EventVO> eventVOs = new ArrayList<>();
		transactionsByEventId.forEach((key, value) -> {
			eventVOs.add(transactionsToEventVO.convert(value));
		});
		return eventVOs;
	}
}
