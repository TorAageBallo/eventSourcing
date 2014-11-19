package com.ballo.core.bank.event;

import com.ballo.core.AggregateType;

public class AccountCreatedEvent extends BankEvent {
    private final String accountNr;

    public AccountCreatedEvent(String accountNr) {
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
