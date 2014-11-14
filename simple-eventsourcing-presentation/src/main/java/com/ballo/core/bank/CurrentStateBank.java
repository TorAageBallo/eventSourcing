package com.ballo.core.bank;

import java.util.HashMap;
import java.util.Map;

public class CurrentStateBank implements BankService {
    static Map<String, Integer> currentStateWay = new HashMap<>();

    public CurrentStateBank() {
        System.out.println("-----CURRENT STATE BANK-----");
    }

    @Override
    public void opprettKonto(String kontonr) {
        currentStateWay.putIfAbsent(kontonr, 0);
        System.out.println("Opprettet konto med kontonr " + kontonr);
    }

    @Override
    public void settInn(Integer beloep, String kontonr) {
        currentStateWay.compute(kontonr, (konto, verdi) -> konto == null ? verdi : verdi + beloep);
        System.out.println("Satt inn " + beloep + " på konto " + kontonr);
    }

    @Override
    public void taUt(Integer beloep, String kontonr) {
        Integer balanse = currentStateWay.get(kontonr);
        if (balanse < beloep) {
            System.out.println("Du har desverre ikke penger til å ta ut " + beloep);
            return;
        }

        currentStateWay.compute(kontonr, (konto, verdi) -> (konto == null) ? verdi : verdi - beloep);
        System.out.println("Tatt ut " + beloep + " fra konto " + kontonr);
    }

    @Override
    public int hentBalanse(String kontonummer) {
        return currentStateWay.get(kontonummer);
    }
}