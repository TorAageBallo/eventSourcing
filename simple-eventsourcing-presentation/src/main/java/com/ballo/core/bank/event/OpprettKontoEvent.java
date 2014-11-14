package com.ballo.core.bank.event;

import com.ballo.core.AggregateType;

public class OpprettKontoEvent extends BankEvent {
    private final String kontonr;

    public OpprettKontoEvent(String kontonr) {
        this.kontonr = kontonr;
    }

    @Override
    public String getAggregateId() {
        return kontonr;
    }

    @Override
    public AggregateType getAggregateType() {
        return AggregateType.KONTO;
    }
}
