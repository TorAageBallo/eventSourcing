package com.ballo.core.bank.aggregate;

import com.ballo.core.bank.event.AddMoneyEvent;
import com.ballo.core.bank.event.BankEvent;
import com.ballo.core.bank.event.CreateAccountEvent;
import com.ballo.core.bank.event.WithdrawMoneyEvent;

import java.util.List;
import java.util.function.Consumer;

public class AccountAggregate extends Aggregate {

    private Integer accountState = null;
    private final String account;

    public AccountAggregate(String account, List<BankEvent> events) {
        this.account = account;
        Consumer<BankEvent> createStateConsumer = bankEvent -> {
            if (bankEvent instanceof CreateAccountEvent)
                updateState((CreateAccountEvent) bankEvent);
            else if (bankEvent instanceof AddMoneyEvent)
                updateState((AddMoneyEvent) bankEvent);
            else if (bankEvent instanceof WithdrawMoneyEvent)
                updateState((WithdrawMoneyEvent) bankEvent);
        };

        events
                .stream()
                .forEach(createStateConsumer);
    }

    public void addMoney(Integer amount) {
        AddMoneyEvent addMoneyEvent = new AddMoneyEvent(amount, account);
        updateState(addMoneyEvent);
        derivedEvents.add(addMoneyEvent);
    }

    public void withDrawMoney(Integer amount) {
        WithdrawMoneyEvent withdrawMoneyEvent = new WithdrawMoneyEvent(amount, account);
        updateState(withdrawMoneyEvent);
        derivedEvents.add(withdrawMoneyEvent);
    }

    public Integer getAccountState() {
        return accountState;
    }

    public void updateState(WithdrawMoneyEvent withdrawMoneyEvent) {
        if (accountState < withdrawMoneyEvent.getAmount()) {
            System.out.println("Du har desverre ikke penger til å ta ut " + withdrawMoneyEvent.getAmount());
            return;
        }

        accountState -= withdrawMoneyEvent.getAmount();
        System.out.println("Tatt ut " + withdrawMoneyEvent.getAmount() + " fra konto " + withdrawMoneyEvent.getAggregateId());
    }

    public void updateState(AddMoneyEvent addMoneyEvent) {
        accountState += addMoneyEvent.getAmount();
        System.out.println("Satt inn " + addMoneyEvent.getAmount() + " på konto " + addMoneyEvent.getAggregateId());
    }

    public void updateState(CreateAccountEvent createAccountEvent) {
        accountState = 0;
        System.out.println("Opprettet konto med kontonr " + createAccountEvent.getAggregateId());
    }
}
