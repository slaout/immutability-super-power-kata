package com.github.slaout.immutability.exercise1.domain.report;

import com.github.slaout.immutability.exercise1.domain.edit.Edit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    private BigDecimal amount;
    private Edit lastAmountEdit;

    private Currency currency;
    private Edit lastCurrencyEdit;
}
