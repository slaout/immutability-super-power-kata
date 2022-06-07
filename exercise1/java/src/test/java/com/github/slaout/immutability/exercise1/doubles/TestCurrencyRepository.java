package com.github.slaout.immutability.exercise1.doubles;

import com.github.slaout.immutability.exercise1.domain.report.Currency;
import com.github.slaout.immutability.exercise1.repository.CurrencyRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

import static com.github.slaout.immutability.exercise1.fixture.ReportFixtures.anyCurrency;

public class TestCurrencyRepository implements CurrencyRepository {

    @Getter
    @Setter
    private Currency defaultCurrency = anyCurrency();

    @Getter
    @Setter
    private Currency knownOtherCurrency = null;

    public List<Currency> getCurrencies() {
        if (knownOtherCurrency == null) {
            return List.of(getDefaultCurrency());
        }

        return List.of(getDefaultCurrency(), knownOtherCurrency);
    }

    public Optional<Currency> getCurrency(String code) {
        return getCurrencies().stream()
                .filter(currency -> currency.getCode().equals(code))
                .findFirst();
    }

}
