package com.ballo.core.bank.aggregate;

import com.ballo.core.bank.event.AddMoneyEvent;
import com.ballo.core.bank.event.BankEvent;
import com.ballo.core.bank.event.CreateAccountEvent;
import com.ballo.core.bank.event.WithdrawMoneyEvent;

import java.util.List;
import java.util.function.Consumer;

public class AccountAllowCreditAggregate extends Aggregate {

    private Integer accountState = null;
    private final String account;

    public AccountAllowCreditAggregate(String account, List<BankEvent> events) {
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

    public void withdrawMoney(Integer amount) {
        WithdrawMoneyEvent withdrawMoneyEvent = new WithdrawMoneyEvent(amount, account);
        updateState(withdrawMoneyEvent);
        derivedEvents.add(withdrawMoneyEvent);
    }

    public Integer getAccountState() {
        return accountState;
    }

    public void updateState(WithdrawMoneyEvent withdrawMoneyEvent) {
        accountState -= withdrawMoneyEvent.getAmount();
        System.out.println("Withdrawn " + withdrawMoneyEvent.getAmount() + " from account " + withdrawMoneyEvent.getAggregateId());
    }

    public void updateState(AddMoneyEvent addMoneyEvent) {
        accountState += addMoneyEvent.getAmount();
        System.out.println("Added " + addMoneyEvent.getAmount() + " to account " + addMoneyEvent.getAggregateId());
    }

    public void updateState(CreateAccountEvent createAccountEvent) {
        accountState = 0;
        System.out.println("Created account with account number " + createAccountEvent.getAggregateId());
    }
}
