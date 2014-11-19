package com.ballo.core.bank.event;

import com.ballo.core.AggregateType;

public class MoneyAddedEvent extends BankEvent {
    private final Integer amount;
    private final String accountNr;

    public MoneyAddedEvent(Integer amount, String accountNr) {
        this.amount = amount;
        this.accountNr = accountNr;
    }

    public Integer getAmount() {
        return amount;
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
