package com.ballo.core.bank.repository;

import com.ballo.core.AggregateType;
import com.ballo.core.bank.event.BankEvent;
import com.ballo.core.bank.projection.Projection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventStore {

    List<BankEvent> bankEvents = new ArrayList<>();
    private Map<AggregateType, List<Projection>> subscribers = new HashMap<>();

    public void store(BankEvent event) {
        bankEvents.add(event);
        publish(event);
    }

    private void publish(BankEvent event) {
        subscribers.get(event.getAggregateType()).stream()
                .forEach(s -> s.handleEvent(event));
    }

    public List<BankEvent> getBankEvents(String aggregateId) {
        return bankEvents
                .stream()
                .filter((bankEvent) -> bankEvent.getAggregateId().equals(aggregateId))
                .collect(Collectors.toList());
    }


    public void subscribe(AggregateType subscribedType, Projection projection) {
        subscribers.putIfAbsent(subscribedType, new ArrayList<>());
        subscribers.get(subscribedType).add(projection);
    }
}
