package com.ballo.core;

import com.ballo.core.bank.BankService;
import com.ballo.core.bank.CurrentStateBank;
import com.ballo.core.bank.EventSourcedBank;

public class SimpleEventSourcingMain {
    public static void main(String[] args) {
         /*--------------------------------------------------------------
        |
        |                           INTRO
        |
        ---------------------------------------------------------------- */

        // I de fleste dagens systmer lagrer vi og vedlikeholder vi nåværende tilstand.
        BankService currentStateBank = new CurrentStateBank();
        currentStateBank.opprettKonto("12345");
        currentStateBank.settInn(100, "12345");
        currentStateBank.taUt(150, "12345");
        currentStateBank.taUt(50, "12345");
        currentStateBank.settInn(25, "12345");
        System.out.println("Nåværende balanse er " + currentStateBank.hentBalanse("12345"));

        // Ett eksempel på hvordan dette gjøres med Event Sourcing
        BankService eventSourcedBank = new EventSourcedBank();
        eventSourcedBank.opprettKonto("12345");
        eventSourcedBank.settInn(100, "12345");
        eventSourcedBank.taUt(150, "12345");
        eventSourcedBank.taUt(50, "12345");
        eventSourcedBank.settInn(25, "12345");
        System.out.println("Nåværende balanse er " + eventSourcedBank.hentBalanse("12345"));
    }
}
