package com.github.slaout.immutability.exercise1.domain.report;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Currency {
    private String code;
    private BigDecimal exchangeRateToEuro;
}
