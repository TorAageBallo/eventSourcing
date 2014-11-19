package com.ballo.core.bank.aggregate.v2;

import com.ballo.core.bank.aggregate.Aggregate;
import com.ballo.core.bank.event.AccountCreatedEvent;
import com.ballo.core.bank.event.MoneyAddedEvent;
import com.ballo.core.bank.event.BankEvent;
import com.ballo.core.bank.event.MoneyWithdrawnEvent;

import java.util.List;
import java.util.function.Consumer;

public class AccountAllowCreditAggregate extends Aggregate {

    private Integer accountState = null;
    private final String account;

    public AccountAllowCreditAggregate(String account, List<BankEvent> events) {
        this.account = account;
        Consumer<BankEvent> createStateConsumer = bankEvent -> {
            if (bankEvent instanceof AccountCreatedEvent)
                updateState((AccountCreatedEvent) bankEvent);
            else if (bankEvent instanceof MoneyAddedEvent)
                updateState((MoneyAddedEvent) bankEvent);
            else if (bankEvent instanceof MoneyWithdrawnEvent)
                updateState((MoneyWithdrawnEvent) bankEvent);
        };

        events
                .stream()
                .forEach(createStateConsumer);
    }

    public void addMoney(Integer amount) {
        MoneyAddedEvent moneyAddedEvent = new MoneyAddedEvent(amount, account);
        updateState(moneyAddedEvent);
        derivedEvents.add(moneyAddedEvent);
    }

    public void withdrawMoney(Integer amount) {
        MoneyWithdrawnEvent moneyWithdrawnEvent = new MoneyWithdrawnEvent(amount, account);
        updateState(moneyWithdrawnEvent);
        derivedEvents.add(moneyWithdrawnEvent);
    }

    public Integer getAccountState() {
        return accountState;
    }

    public void updateState(MoneyWithdrawnEvent moneyWithdrawnEvent) {
        accountState -= moneyWithdrawnEvent.getAmount();
        System.out.println("Withdrawn " + moneyWithdrawnEvent.getAmount() + " from account " + moneyWithdrawnEvent.getAggregateId());
    }

    public void updateState(MoneyAddedEvent moneyAddedEvent) {
        accountState += moneyAddedEvent.getAmount();
        System.out.println("Added " + moneyAddedEvent.getAmount() + " to account " + moneyAddedEvent.getAggregateId());
    }

    public void updateState(AccountCreatedEvent accountCreatedEvent) {
        accountState = 0;
        System.out.println("Created account with account number " + accountCreatedEvent.getAggregateId());
    }
}
