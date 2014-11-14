package com.ballo.core.bank.event;

import com.ballo.core.AggregateType;

public class AddMoneyEvent extends BankEvent {
    private final Integer beloep;
    private final String kontonr;

    public AddMoneyEvent(Integer beloep, String kontonr) {
        this.beloep = beloep;
        this.kontonr = kontonr;
    }

    public Integer getBeloep() {
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
