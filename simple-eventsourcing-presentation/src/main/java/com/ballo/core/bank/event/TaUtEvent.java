package com.ballo.core.bank.event;

import com.ballo.core.AggregateType;

public class TaUtEvent extends BankEvent {
    private final Integer beloep;
    private final String kontonr;

    public TaUtEvent(Integer beloep, String kontonr) {
        this.beloep = beloep;
        this.kontonr = kontonr;
    }

    public int getBeloep() {
        return beloep;
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
