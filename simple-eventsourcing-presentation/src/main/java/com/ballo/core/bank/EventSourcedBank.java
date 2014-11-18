package com.ballo.core.bank;

import com.ballo.core.bank.event.AddMoneyEvent;
import com.ballo.core.bank.event.CreateAccountEvent;
import com.ballo.core.bank.event.WithdrawMoneyEvent;
import com.ballo.core.bank.projection.Projection;
import com.ballo.core.bank.repository.EventStore;

public class EventSourcedBank {

    EventStore eventStore;
    Projection accountProjection;

    public EventSourcedBank(EventStore eventStore, Projection projection) {
        this.eventStore = eventStore;
        this.accountProjection = projection;
        System.out.println("-----EVENT BANK-----");
    }

    public void createAccount(String accountNr) {
        eventStore.store(new CreateAccountEvent(accountNr));
    }

    public void addMoney(Integer amount, String accountNr) {
        eventStore.store(new AddMoneyEvent(amount, accountNr));
    }

    public void withdrawMoney(Integer amount, String accountNr) {
        eventStore.store(new WithdrawMoneyEvent(amount, accountNr));
    }

    public int getBalance(String accountNr) {
        return accountProjection.getAccountBalance(accountNr);
    }

}