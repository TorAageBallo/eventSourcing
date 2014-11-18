package com.ballo.core.bank.event;

import com.ballo.core.AggregateType;

public class CreateAccountEvent extends BankEvent {
    private final String accountNr;

    public CreateAccountEvent(String accountNr) {
        this.accountNr = accountNr;
    }

    @Override
    public String getAggregateId() {
        return accountNr;
    }

    @Override
    public AggregateType getAggregateType() {
        return AggregateType.ACCOUNT;
    }
}
