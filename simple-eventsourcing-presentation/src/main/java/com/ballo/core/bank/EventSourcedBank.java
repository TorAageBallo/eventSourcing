package com.ballo.core.bank;

import com.ballo.core.bank.event.OpprettKontoEvent;
import com.ballo.core.bank.event.SettInnPengerEvent;
import com.ballo.core.bank.event.TaUtEvent;
import com.ballo.core.bank.repository.EventStore;

public class EventSourcedBank implements BankService {

        EventStore eventStore;

    public EventSourcedBank() {
        this.eventStore = new EventStore();
        System.out.println("-----EVENT BANK-----");
    }

    @Override
        public void opprettKonto(String kontonr) {
            eventStore.store(new OpprettKontoEvent(kontonr));
            System.out.println("Opprettet konto med kontonr " + kontonr);
        }

        @Override
        public void settInn(Integer beloep, String kontonr) {
            eventStore.store(new SettInnPengerEvent(beloep, kontonr));
            System.out.println("Satt inn " + beloep + " på konto " + kontonr);
        }

        @Override
        public void taUt(Integer beloep, String kontonr) {
            eventStore.store(new TaUtEvent(beloep, kontonr));
            System.out.println("Tatt ut " + beloep + " fra konto " + kontonr);

        }

        @Override
        public int hentBalanse(String kontonummer) {
            return 0;
        }
    }