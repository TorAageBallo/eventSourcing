package com.ballo.core.bank;

import com.ballo.core.bank.event.AccountCreatedEvent;
import com.ballo.core.bank.event.MoneyAddedEvent;
import com.ballo.core.bank.event.MoneyWithdrawnEvent;
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
        eventStore.store(new AccountCreatedEvent(accountNr));
    }

    public void addMoney(Integer amount, String accountNr) {
        eventStore.store(new MoneyAddedEvent(amount, accountNr));
    }

    public void withdrawMoney(Integer amount, String accountNr) {
        eventStore.store(new MoneyWithdrawnEvent(amount, accountNr));
    }

    public int getBalance(String accountNr) {
        return accountProjection.getAccountBalance(accountNr);
    }

}