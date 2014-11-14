package com.ballo.core.bank.projection;

import com.ballo.core.AggregateType;
import com.ballo.core.bank.event.BankEvent;
import com.ballo.core.bank.repository.EventStore;

public abstract class Projection {

    public Projection(EventStore eventStore) {
        eventStore.subscribe(getSubscribedType(), this);
    }

    protected abstract AggregateType getSubscribedType();

    public abstract void handleEvent(BankEvent event);
}