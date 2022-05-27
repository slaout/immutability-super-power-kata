package com.github.slaout.immutability.exercise1.doubles;

import com.github.slaout.immutability.exercise1.domain.report.Currency;
import com.github.slaout.immutability.exercise1.repository.CurrencyRepository;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.github.slaout.immutability.exercise1.fixture.ReportFixtures.ANY_CURRENCY;

public class TestCurrencyRepository implements CurrencyRepository {

    @Getter
    @Setter
    private Currency defaultCurrency = ANY_CURRENCY;

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
