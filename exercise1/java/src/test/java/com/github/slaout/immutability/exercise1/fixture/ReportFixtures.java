package com.github.slaout.immutability.exercise1.fixture;

import com.github.slaout.immutability.exercise1.domain.report.Currency;
import com.github.slaout.immutability.exercise1.domain.report.Price;
import com.github.slaout.immutability.exercise1.domain.report.PriceReport;
import com.github.slaout.immutability.exercise1.domain.report.Seller;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

import static com.github.slaout.immutability.exercise1.fixture.EditFixtures.ANY_EDIT;
import static com.github.slaout.immutability.exercise1.fixture.ProductFixtures.KNOWN_PRODUCT;

@UtilityClass
public class ReportFixtures {

    public static final int KNOWN_SELLER_ID = 2;
    public static final int ANOTHER_KNOWN_SELLER_ID = 3;
    public static final int UNKNOWN_SELLER_ID = 404;

    public static final Seller KNOWN_SELLER = new Seller(KNOWN_SELLER_ID, "Any name");
    public static final Seller ANOTHER_KNOWN_SELLER = new Seller(ANOTHER_KNOWN_SELLER_ID, "Any other name");

    public static final Currency ANY_CURRENCY = new Currency("ANY", new BigDecimal("0.42"));
    public static final BigDecimal ANY_AMOUNT = new BigDecimal("42");
    public static final Price ANY_PRICE = Price.restoreFromDatabase(ANY_AMOUNT, ANY_EDIT, ANY_CURRENCY, ANY_EDIT);
    // TODO methods to be able to generate unique anyAmount()?

    public static final String DEFAULT_CURRENCY_CODE = "DEFAULT_CURRENCY_CODE";
    public static final BigDecimal DEFAULT_CURRENCY_EXCHANGE_RATE = new BigDecimal("8");
    public static final Currency DEFAULT_CURRENCY = new Currency(DEFAULT_CURRENCY_CODE, DEFAULT_CURRENCY_EXCHANGE_RATE);

    public static final PriceReport EXISTING_REPORT_FOR_KNOWN_PRODUCT_AND_SELLER = new PriceReport(KNOWN_PRODUCT, KNOWN_SELLER, ANY_PRICE);

}
