package com.ballo.core.bank;

public interface BankService {
        void opprettKonto(String kontonr);

        void settInn(Integer beloep, String kontonr);

        void taUt(Integer beloep, String kontonr);

        int hentBalanse(String kontonummer);
    }