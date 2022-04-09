package com.github.slaout.immutability.exercise1.domain.report;

import com.github.slaout.immutability.exercise1.domain.edit.Edit;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class Price {
    BigDecimal amount;
    Edit lastAmountEdit;

    Currency currency;
    Edit lastCurrencyEdit;

    public static Price create(Currency currency, Edit creationEdit) {
        return new Price(
                null, // At creation, user has not entered any amount yet
                creationEdit,
                currency,
                creationEdit);
    }

    public Price withEditedAmount(BigDecimal editedAmount, Edit edit) {
        return new Price(
                editedAmount,
                edit,
                this.currency,
                this.lastCurrencyEdit);
    }

    public Price withEditedCurrency(Currency editedCurrency, Edit edit) {
        return new Price(
                this.amount,
                this.lastAmountEdit,
                editedCurrency,
                edit);
    }

    public Price withSyncedExchangeRateToEuro(BigDecimal syncedExchangeRateToEuro) {
        return new Price(
                this.amount,
                this.lastAmountEdit,
                currency.withExchangeRateToEuro(syncedExchangeRateToEuro),
                this.lastCurrencyEdit);
    }

}
