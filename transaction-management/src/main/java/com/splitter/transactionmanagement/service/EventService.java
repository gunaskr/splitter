package com.splitter.transactionmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.splitter.transactionmanagement.controller.dto.EventVO;
import com.splitter.transactionmanagement.converter.impl.EventToEventVO;
import com.splitter.transactionmanagement.converter.impl.EventVOToEvent;
import com.splitter.transactionmanagement.converter.impl.TransactionToTransactionVO;
import com.splitter.transactionmanagement.converter.impl.TransactionVOToTransaction;
import com.splitter.transactionmanagement.model.Event;
import com.splitter.transactionmanagement.model.Transaction;
import com.splitter.transactionmanagement.repository.EventRepository;
import com.splitter.transactionmanagement.repository.TransactionRepository;

@Service
public class EventService {
	
	private final TransactionRepository transactionRepository;
	private final EventRepository eventRepository;
	private final EventVOToEvent eventVOToEvent;
	private final EventToEventVO eventToEventVO;
	private final TransactionToTransactionVO transactionToTransactionVO;
	
	@Autowired
	public EventService(final TransactionRepository transactionRepository,
			final EventRepository eventRepository,
			final EventVOToEvent eventVOToEvent,
			final EventToEventVO eventToEventVO,
			final TransactionToTransactionVO transactionToTransactionVO) {
		super();
		this.transactionRepository = transactionRepository;
		this.eventRepository = eventRepository;
		this.eventVOToEvent = eventVOToEvent;
		this.eventToEventVO = eventToEventVO;
		this.transactionToTransactionVO = transactionToTransactionVO;
	}
	
	//TODO works when we use replica set @Transactional
	public EventVO createEvent(final EventVO eventRequest) {
		final Event savedEvent = eventRepository.save(eventVOToEvent.convert(eventRequest));
		
		final TransactionVOToTransaction transactionRequestToTransaction = new TransactionVOToTransaction(savedEvent);
		final List<Transaction> transactions = eventRequest.getTransactions().stream().map(transactionRequestToTransaction::convert).collect(Collectors.toList());
		List<Transaction> savedTransactions = transactionRepository.saveAll(transactions);
		
		final EventVO savedEventVO = eventToEventVO.convert(savedEvent);
		savedEventVO.setTransactions(savedTransactions.stream().map(transactionToTransactionVO::convert).collect(Collectors.toList()));
		return savedEventVO;
	}

	public EventVO findEventById(String eventId) {
		final List<Transaction> transactions = transactionRepository.findByEventId(new ObjectId(eventId));
		
		if(transactions.isEmpty()) {
			return null;
		}
		
		final EventVO savedEventVO = eventToEventVO.convert(transactions.get(0).getEvent());
		savedEventVO.setTransactions(transactions.stream().map(transactionToTransactionVO::convert).collect(Collectors.toList()));
		
		return savedEventVO;
	}

	public  List<EventVO> filterEvents(String fromUserId, String toUserId) {
		List<Transaction> transactions = transactionRepository.findByFromUserIdOrToUserId(fromUserId, toUserId);
		Map<String, List<Transaction>> transactionsByEventId = transactions.stream().collect(Collectors.groupingBy((t) -> {
		 return t.getEvent().getId();
		}));
		final List<EventVO> eventVOs = new ArrayList<>();
		transactionsByEventId.forEach((key, value) -> {
			final EventVO savedEventVO = eventToEventVO.convert(value.get(0).getEvent());
			savedEventVO.setTransactions(value.stream().map(transactionToTransactionVO::convert).collect(Collectors.toList()));
			eventVOs.add(savedEventVO);
		});
		return eventVOs;
	}
}
