package com.github.slaout.immutability.exercise1.domain.report;

import lombok.Value;
import lombok.With;

import java.math.BigDecimal;

@Value
public class Currency {
    String code;

    @With
    BigDecimal exchangeRateToEuro;
}
