package com.ballo.core.bank.projection;

import com.ballo.core.AggregateType;
import com.ballo.core.bank.aggregate.AccountAllowCreditAggregate;
import com.ballo.core.bank.event.AddMoneyEvent;
import com.ballo.core.bank.event.BankEvent;
import com.ballo.core.bank.event.CreateAccountEvent;
import com.ballo.core.bank.event.WithdrawMoneyEvent;
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
        return AggregateType.KONTO;
    }

    @Override
    public void handleEvent(BankEvent event) {
        if (event instanceof CreateAccountEvent) {
            handleCreateAccountEvent(event);
        } else if (event instanceof AddMoneyEvent) {
            handleAddMoneyEvent((AddMoneyEvent) event);
        } else if (event instanceof WithdrawMoneyEvent) {
            handleWithdrawMoneyEvent((WithdrawMoneyEvent) event);
        }
    }

    private void handleWithdrawMoneyEvent(WithdrawMoneyEvent event) {
        AccountAllowCreditAggregate aggregate = accountState.get(event.getAggregateId());
        aggregate.updateState(event);
    }

    private void handleAddMoneyEvent(AddMoneyEvent event) {
        AccountAllowCreditAggregate aggregate = accountState.get(event.getAggregateId());
        aggregate.updateState(event);
    }

    private void handleCreateAccountEvent(BankEvent event) {
        accountState.putIfAbsent(event.getAggregateId(), new AccountAllowCreditAggregate(event.getAggregateId(), Arrays.asList(event)));
    }

}

