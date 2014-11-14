package com.ballo.core.bank;

import com.ballo.core.bank.event.AddMoneyEvent;
import com.ballo.core.bank.event.CreateAccountEvent;
import com.ballo.core.bank.event.WithdrawMoneyEvent;
import com.ballo.core.bank.projection.AccountProjection;
import com.ballo.core.bank.repository.EventStore;

public class EventSourcedBank implements BankService {

    EventStore eventStore;
    AccountProjection accountProjection;

    public EventSourcedBank() {
        this.eventStore = new EventStore();
        this.accountProjection = new AccountProjection(eventStore);
        System.out.println("-----EVENT BANK-----");
    }

    @Override
    public void opprettKonto(String kontonr) {
        eventStore.store(new CreateAccountEvent(kontonr));
        System.out.println("Opprettet konto med kontonr " + kontonr);
    }

    @Override
    public void settInn(Integer beloep, String kontonr) {
        eventStore.store(new AddMoneyEvent(beloep, kontonr));
        System.out.println("Satt inn " + beloep + " på konto " + kontonr);
    }

    @Override
    public void taUt(Integer beloep, String kontonr) {
        eventStore.store(new WithdrawMoneyEvent(beloep, kontonr));
        System.out.println("Tatt ut " + beloep + " fra konto " + kontonr);
    }

    @Override
    public int hentBalanse(String kontonummer) {
        return accountProjection.getAccountBalance(kontonummer);
    }
}