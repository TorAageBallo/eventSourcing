package com.ballo.core.bank.projection;

import com.ballo.core.AggregateType;
import com.ballo.core.bank.event.AddMoneyEvent;
import com.ballo.core.bank.event.BankEvent;
import com.ballo.core.bank.event.CreateAccountEvent;
import com.ballo.core.bank.event.WithdrawMoneyEvent;
import com.ballo.core.bank.repository.EventStore;

import java.util.HashMap;
import java.util.Map;

public class AccountProjection extends Projection {

    static Map<String, Integer> accountState = new HashMap<>();

    public AccountProjection(EventStore eventStore) {
        super(eventStore);
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
        Integer balanse = accountState.get(event.getAggregateId());
        if (balanse < event.getAmount()) {
            System.out.println("Du har desverre ikke penger til å ta ut " + event.getAmount());
            return;
        }

        accountState.compute(event.getAggregateId(), (konto, verdi) -> (konto == null) ? verdi : verdi - event.getAmount());
        System.out.println("Tatt ut " + event.getAmount() + " fra konto " + event.getAggregateId());
    }

    private void handleAddMoneyEvent(AddMoneyEvent event) {
        accountState.compute(event.getAggregateId(), (konto, verdi) -> konto == null ? verdi : verdi + event.getAmount());
        System.out.println("Satt inn " + event.getAmount() + " på konto " + event.getAggregateId());
    }

    private void handleCreateAccountEvent(BankEvent event) {
        accountState.putIfAbsent(event.getAggregateId(), 0);
        System.out.println("Opprettet konto med kontonr " + event.getAggregateId());
    }

    public Integer getAccountBalance(String aggregateId) {
        return accountState.getOrDefault(aggregateId, 0);
    }

}

