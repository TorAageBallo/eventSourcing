package com.ballo.core.bank.event;

import com.ballo.core.AggregateType;

public class MoneyWithdrawnEvent extends BankEvent {
    private final Integer amount;
    private final String accountNr;

    public MoneyWithdrawnEvent(Integer amount, String accountNr) {
        this.amount = amount;
        this.accountNr = accountNr;
    }

    public int getAmount() {
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
