package com.ballo.core.bank.aggregate;

import com.ballo.core.bank.event.BankEvent;

import java.util.ArrayList;
import java.util.List;

public class Aggregate {

    protected List<BankEvent> derivedEvents = new ArrayList<>();

    public List<BankEvent> getDerivedEvents() {
        return derivedEvents;
    }
}
