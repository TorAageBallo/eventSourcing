package com.ballo.core;

import com.ballo.core.bank.CurrentStateBank;
import com.ballo.core.bank.EventSourcedBank;
import com.ballo.core.bank.aggregate.AccountAggregate;
import com.ballo.core.bank.projection.AccountAllowCreditProjection;
import com.ballo.core.bank.projection.AccountProjection;
import com.ballo.core.bank.repository.EventStore;

public class SimpleEventSourcingMain {
    public static void main(String[] args) {
         /*--------------------------------------------------------------
        |
        |                           INTRO
        |
        ---------------------------------------------------------------- */

        // I de fleste dagens systmer lagrer vi og vedlikeholder vi nåværende tilstand.
        String kontonr = "12345";
        CurrentStateBank currentStateBank = new CurrentStateBank();
        currentStateBank.opprettKonto(kontonr);
        currentStateBank.settInn(100, kontonr);
        currentStateBank.taUt(150, kontonr);
        currentStateBank.taUt(50, kontonr);
        currentStateBank.settInn(25, kontonr);
        System.out.println("Nåværende balanse er " + currentStateBank.hentBalanse(kontonr));

        // Ett eksempel på hvordan dette gjøres med Event
        EventStore eventStore = new EventStore();
        EventSourcedBank eventSourcedBank = new EventSourcedBank(eventStore, new AccountProjection(eventStore));
        eventSourcedBank.opprettKonto(kontonr);
        eventSourcedBank.settInn(100, kontonr);
        eventSourcedBank.taUt(150, kontonr);
        eventSourcedBank.taUt(50, kontonr);
        eventSourcedBank.settInn(25, kontonr);
        System.out.println("Nåværende balanse er " + eventSourcedBank.hentBalanse(kontonr));

        // Hva hvis vi har et brukergrensesnitt som skal jobbe på balansen på konto og gjøre endringer uten at det skal lagres?
        System.out.println("-----AGGREGATE-----");
        AccountAggregate accountAggregate = eventSourcedBank.getAccountAggregate(kontonr);
        System.out.println("Nåværende account state for konto " + kontonr + " er " + accountAggregate.getAccountState());
        accountAggregate.addMoney(10_000);
        System.out.println("Nåværende account state for konto " + kontonr + " er " + accountAggregate.getAccountState());
        accountAggregate.withDrawMoney(10_000);
        accountAggregate.addMoney(1_000);
        System.out.println("Nåværende account state for konto " + kontonr + " er " + accountAggregate.getAccountState());
        eventStore.store(accountAggregate.getDerivedEvents());

        System.out.println("-----NEW AGGREGATE-----");
        eventSourcedBank.getAccountAggregate(kontonr);
        System.out.println("Nåværende balanse er " + eventSourcedBank.hentBalanse(kontonr));

        // Produkteier ønsker nå å ha funksjonalitet for å gi kreditt til sine kunder
        System.out.println("-----ALLOW CREDIT USER STORY-----");
        AccountAllowCreditProjection projection = new AccountAllowCreditProjection(eventStore);
        eventStore.getBankEvents(kontonr)
                .stream()
                .forEach(projection::handleEvent);
        System.out.println("Nåværende balanse er " + projection.getAccountBalance(kontonr));
    }
}
