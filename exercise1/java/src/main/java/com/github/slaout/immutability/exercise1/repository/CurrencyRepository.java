package com.github.slaout.immutability.exercise1.repository;

import com.github.slaout.immutability.exercise1.domain.report.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository {

    Currency getDefaultCurrency();

    List<Currency> getCurrencies();

    Optional<Currency> getCurrency(String code);

}
