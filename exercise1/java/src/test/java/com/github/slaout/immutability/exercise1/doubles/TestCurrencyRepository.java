package com.github.slaout.immutability.exercise1.doubles;

import com.github.slaout.immutability.exercise1.domain.report.Currency;
import com.github.slaout.immutability.exercise1.repository.CurrencyRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class TestCurrencyRepository implements CurrencyRepository {

    public Currency getDefaultCurrency() {
        return new Currency("EUR", BigDecimal.ONE);
    }

    public List<Currency> getCurrencies() {
        return List.of(
                getDefaultCurrency(),
                new Currency("CNY", new BigDecimal("0.14")));
    }

    public Optional<Currency> getCurrency(String code) {
        return getCurrencies().stream()
                .filter(currency -> currency.getCode().equals(code))
                .findFirst();
    }

}
