package com.ballo.core.bank;

import java.util.HashMap;
import java.util.Map;

public class CurrentStateBank {
    static Map<String, Integer> currentStateWay = new HashMap<>();

    public CurrentStateBank() {
        System.out.println("-----CURRENT STATE BANK-----");
    }

    public void createAccount(String accountNr) {
        currentStateWay.putIfAbsent(accountNr, 0);
        System.out.println("Created account with account number " + accountNr);
    }

    public void addMoney(Integer amount, String accountNr) {
        currentStateWay.compute(accountNr, (account, currentBalance) -> currentBalance + amount);
        System.out.println("Added " + amount + " to account " + accountNr);
    }

    public void withdrawMoney(Integer amount, String accountNr) {
        Integer currentBalance = currentStateWay.getOrDefault(accountNr, 0);
        if (currentBalance < amount) {
            System.out.println("I'm sorry but you can't withdraw " + amount + " as your current balance is " + currentBalance);
            return;
        }

        currentStateWay.compute(accountNr, (account, balance) -> balance - amount);
        System.out.println("Withdrawn " + amount + " from account " + accountNr);
    }

    public int getBalance(String accountNr) {
        return currentStateWay.get(accountNr);
    }
}