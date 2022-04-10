package com.github.slaout.immutability.exercise1.domain.report;

import com.github.slaout.immutability.exercise1.domain.edit.Edit;
import com.github.slaout.immutability.exercise1.domain.edit.Editable;
import com.github.slaout.immutability.exercise1.domain.edit.NullableEditable;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class Price {
    NullableEditable<BigDecimal> amount;
    Editable<Currency> currency;

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
        this(
                NullableEditable.of(amount, lastAmountEdit),
                Editable.of(currency, lastCurrencyEdit));
    }

    private Price(NullableEditable<BigDecimal> amount, Editable<Currency> currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Price withEditedAmount(BigDecimal editedAmount, Edit edit) {
        return new Price(
                NullableEditable.withEditedValue(this.amount, editedAmount, edit),
                this.currency);
    }

    public Price withEditedCurrency(Currency editedCurrency, Edit edit) {
        return new Price(
                this.amount,
                this.currency.withEditedValue(editedCurrency, edit));
    }

    public Price withSyncedExchangeRateToEuro(BigDecimal syncedExchangeRateToEuro) {
        return new Price(
                this.amount,
                currency.withSyncedValue(currency.getValue().withExchangeRateToEuro(syncedExchangeRateToEuro)));
    }

}
