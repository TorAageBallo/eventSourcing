package com.ballo.core.bank.event;

import com.ballo.core.AggregateType;

public abstract class BankEvent {

    public abstract String getAggregateId();

    public abstract AggregateType getAggregateType();

}
