package com.ballo.core.bank.repository;

import com.ballo.core.bank.event.BankEvent;

import java.util.ArrayList;
import java.util.List;

public class EventStore {

    List<BankEvent> bankEvents = new ArrayList<>();

    public void store(BankEvent event) {
        bankEvents.add(event);
    }
}
