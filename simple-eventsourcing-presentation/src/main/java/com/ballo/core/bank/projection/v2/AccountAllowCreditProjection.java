package com.ballo.core.bank.projection.v2;

import com.ballo.core.AggregateType;
import com.ballo.core.bank.aggregate.v2.AccountAllowCreditAggregate;
import com.ballo.core.bank.event.AccountCreatedEvent;
import com.ballo.core.bank.event.MoneyAddedEvent;
import com.ballo.core.bank.event.BankEvent;
import com.ballo.core.bank.event.MoneyWithdrawnEvent;
import com.ballo.core.bank.projection.Projection;
import com.ballo.core.bank.repository.EventStore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AccountAllowCreditProjection extends Projection {

    static Map<String, AccountAllowCreditAggregate> accountState = new HashMap<>();

    public AccountAllowCreditProjection(EventStore eventStore) {
        super(eventStore);
    }

    @Override
    public Integer getAccountBalance(String aggregateId) {
        return AccountAllowCreditProjection.accountState.get(aggregateId).getAccountState();
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
        AccountAllowCreditAggregate aggregate = accountState.get(event.getAggregateId());
        aggregate.updateState(event);
    }

    private void handleAddMoneyEvent(MoneyAddedEvent event) {
        AccountAllowCreditAggregate aggregate = accountState.get(event.getAggregateId());
        aggregate.updateState(event);
    }

    private void handleCreateAccountEvent(BankEvent event) {
        accountState.putIfAbsent(event.getAggregateId(), new AccountAllowCreditAggregate(event.getAggregateId(), Arrays.asList(event)));
    }

}

