package com.github.slaout.immutability.exercise1.fixture;

import com.github.slaout.immutability.exercise1.domain.report.Currency;
import com.github.slaout.immutability.exercise1.domain.report.Price;
import com.github.slaout.immutability.exercise1.domain.report.PriceReport;
import com.github.slaout.immutability.exercise1.domain.report.Seller;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

import static com.github.slaout.immutability.exercise1.fixture.EditFixtures.anyEdit;
import static com.github.slaout.immutability.exercise1.fixture.ProductFixtures.KNOWN_PRODUCT;

@UtilityClass
public class ReportFixtures {

    public static final long KNOWN_SELLER_ID = 2;
    public static final long ANOTHER_KNOWN_SELLER_ID = 3;
    public static final long UNKNOWN_SELLER_ID = 404;

    public static final Seller KNOWN_SELLER = new Seller(KNOWN_SELLER_ID, "Any name");
    public static final Seller ANOTHER_KNOWN_SELLER = new Seller(ANOTHER_KNOWN_SELLER_ID, "Any other name");

    public static final String ANY_CURRENCY_CODE = "ANY";
    public static final String UNKNOWN_CURRENCY_CODE = "UNKNOWN";
    public static final BigDecimal ANY_EXCHANGE_RATE_TO_EURO = new BigDecimal("0.42");
    public static Currency anyCurrency() {
        return new Currency(ANY_CURRENCY_CODE, ANY_EXCHANGE_RATE_TO_EURO);
    }
    public static final BigDecimal ANY_AMOUNT = new BigDecimal("42");
    public static Price anyPrice() {
        return Price.restoreFromDatabase(ANY_AMOUNT, anyEdit(), anyCurrency(), anyEdit());
    }
    // TODO methods to be able to generate unique anyAmount()?

    public static final String DEFAULT_CURRENCY_CODE = "DEFAULT_CURRENCY_CODE";
    public static final BigDecimal DEFAULT_CURRENCY_EXCHANGE_RATE = new BigDecimal("8");
    public static final Currency DEFAULT_CURRENCY = new Currency(DEFAULT_CURRENCY_CODE, DEFAULT_CURRENCY_EXCHANGE_RATE);

    public static PriceReport existingReportForKnownProductAndSeller() {
        return new PriceReport(KNOWN_PRODUCT, KNOWN_SELLER, anyPrice());
    }

}
