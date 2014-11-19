package com.ballo.core.bank.projection;

import com.ballo.core.AggregateType;
import com.ballo.core.bank.aggregate.AccountAggregate;
import com.ballo.core.bank.event.AccountCreatedEvent;
import com.ballo.core.bank.event.MoneyAddedEvent;
import com.ballo.core.bank.event.BankEvent;
import com.ballo.core.bank.event.MoneyWithdrawnEvent;
import com.ballo.core.bank.repository.EventStore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AccountProjection extends Projection {

    static Map<String, AccountAggregate> accountState = new HashMap<>();
    public AccountProjection(EventStore eventStore) {
        super(eventStore);
    }

    @Override
    public Integer getAccountBalance(String aggregateId) {
        return AccountProjection.accountState.get(aggregateId).getAccountState();
    }

    @Override
    protected AggregateType getSubscribedType() {
        return AggregateType.ACCOUNT;
    }

    @Override
    public void handleEvent(BankEvent event) {
        if (event instanceof AccountCreatedEvent) {
            handleCreateAccountEvent(event);
        } else if (event instanceof MoneyAddedEvent) {
            handleAddMoneyEvent((MoneyAddedEvent) event);
        } else if (event instanceof MoneyWithdrawnEvent) {
            handleWithdrawMoneyEvent((MoneyWithdrawnEvent) event);
        }
    }

    private void handleWithdrawMoneyEvent(MoneyWithdrawnEvent event) {
        AccountAggregate aggregate = accountState.get(event.getAggregateId());
        aggregate.updateState(event);
    }

    private void handleAddMoneyEvent(MoneyAddedEvent event) {
        AccountAggregate aggregate = accountState.get(event.getAggregateId());
        aggregate.updateState(event);
    }

    private void handleCreateAccountEvent(BankEvent event) {
        accountState.putIfAbsent(event.getAggregateId(), new AccountAggregate(event.getAggregateId(), Arrays.asList(event)));
    }


}

