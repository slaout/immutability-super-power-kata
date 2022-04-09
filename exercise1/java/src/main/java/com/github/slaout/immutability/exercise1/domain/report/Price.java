package com.github.slaout.immutability.exercise1.domain.report;

import com.github.slaout.immutability.exercise1.domain.edit.Edit;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Objects;

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

    public static Price restoreFromDatabase(BigDecimal amount,
                                            Edit lastAmountEdit,
                                            Currency currency,
                                            Edit lastCurrencyEdit) {
        return new Price(amount, lastAmountEdit, currency, lastCurrencyEdit);
    }

    private Price(BigDecimal amount, Edit lastAmountEdit, Currency currency, Edit lastCurrencyEdit) {
        this.amount = amount; // Null if user did not enter anything yet, or if user erased the input
        this.lastAmountEdit = Objects.requireNonNull(lastAmountEdit, "lastAmountEdit");
        this.currency = Objects.requireNonNull(currency, "currency");
        this.lastCurrencyEdit = Objects.requireNonNull(lastCurrencyEdit, "lastCurrencyEdit");
    }

    public Price withEditedAmount(BigDecimal editedAmount, Edit edit) {
        if (Objects.equals(this.amount, editedAmount)) {
            return this;
        }

        return new Price(
                editedAmount,
                edit,
                this.currency,
                this.lastCurrencyEdit);
    }

    public Price withEditedCurrency(Currency editedCurrency, Edit edit) {
        if (Objects.equals(this.currency, editedCurrency)) {
            return this;
        }

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
