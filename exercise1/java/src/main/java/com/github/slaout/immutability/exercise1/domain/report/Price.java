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
}
