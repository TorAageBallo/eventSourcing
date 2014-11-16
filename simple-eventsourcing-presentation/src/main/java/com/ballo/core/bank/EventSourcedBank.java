package com.ballo.core.bank;

import com.ballo.core.bank.aggregate.AccountAggregate;
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

    public void opprettKonto(String kontonr) {
        eventStore.store(new CreateAccountEvent(kontonr));
    }

    public void settInn(Integer beloep, String kontonr) {
        eventStore.store(new AddMoneyEvent(beloep, kontonr));
    }

    public void taUt(Integer beloep, String kontonr) {
        eventStore.store(new WithdrawMoneyEvent(beloep, kontonr));
    }

    public int hentBalanse(String kontonummer) {
        return accountProjection.getAccountBalance(kontonummer);
    }

    public AccountAggregate getAccountAggregate(String kontonummer) {
        return new AccountAggregate(kontonummer, eventStore.getBankEvents(kontonummer));
    }
}