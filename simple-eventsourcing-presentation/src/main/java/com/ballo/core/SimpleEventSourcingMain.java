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
        |          Current State Bank
        |
        ---------------------------------------------------------------- */
        // In most of our system we obtain state like this
        String accountNr = "12345";
        CurrentStateBank currentStateBank = new CurrentStateBank();
        currentStateBank.createAccount(accountNr);
        currentStateBank.addMoney(100, accountNr);
        currentStateBank.withdrawMoney(150, accountNr);
        currentStateBank.withdrawMoney(50, accountNr);
        currentStateBank.addMoney(25, accountNr);
        writeCurrentBalance(currentStateBank.getBalance(accountNr));

        /*--------------------------------------------------------------
        |
        |          Event Sourced Bank
        |
        ---------------------------------------------------------------- */
        // This is how it can be done with Event Sourcing
        EventStore eventStore = new EventStore();
        EventSourcedBank eventSourcedBank = new EventSourcedBank(eventStore, new AccountProjection(eventStore));
        eventSourcedBank.createAccount(accountNr);
        eventSourcedBank.addMoney(100, accountNr);
        eventSourcedBank.withdrawMoney(150, accountNr);
        eventSourcedBank.withdrawMoney(50, accountNr);
        eventSourcedBank.addMoney(25, accountNr);
        writeCurrentBalance(eventSourcedBank.getBalance(accountNr));

        /*--------------------------------------------------------------
        |
        |          Aggregate
        |
        ---------------------------------------------------------------- */
        // What if we have a web page where wee want to see the effect of changes before we store our change?
        System.out.println("-----AGGREGATE-----");
        AccountAggregate accountAggregate = new AccountAggregate(accountNr, eventStore.getBankEvents(accountNr));
        writeCurrentBalance(accountAggregate.getAccountState());

        System.out.println("-----Changes from our web page-----");
        accountAggregate.addMoney(10_000);
        writeCurrentBalanceToTheWebPage(accountAggregate.getAccountState());
        accountAggregate.withdrawMoney(10_000);
        accountAggregate.addMoney(1_000);
        writeCurrentBalanceToTheWebPage( accountAggregate.getAccountState());

        System.out.println("-----We then hit the store changes button in our web gui-----");
        eventStore.store(accountAggregate.getDerivedEvents());

        System.out.println("-----We now retrieve our aggregate again-----");
        AccountAggregate newAccountAggregate = new AccountAggregate(accountNr, eventStore.getBankEvents(accountNr));
        writeCurrentBalance(newAccountAggregate.getAccountState());

        /*--------------------------------------------------------------
        |
        |          ALLOW CREDIT
        |
        ---------------------------------------------------------------- */
        // Our Event Store Bank now wants to allow unlimited credit to their customers.
        System.out.println("-----ALLOW CREDIT USER STORY-----");
        // We create a new projection that allows credit, and run all our events through it to see what effect it will have
        AccountAllowCreditProjection projection = new AccountAllowCreditProjection(eventStore);
        eventStore.getBankEvents(accountNr)
                .stream()
                .forEach(projection::handleEvent);
        writeCurrentBalance(projection.getAccountBalance(accountNr));
    }

    private static void writeCurrentBalanceToTheWebPage(Integer accountState) {
        System.out.println("Current balance in our web page is " + accountState);
    }

    private static void writeCurrentBalance(int balance) {
        System.out.println("Current balance is " + balance);
    }
}
