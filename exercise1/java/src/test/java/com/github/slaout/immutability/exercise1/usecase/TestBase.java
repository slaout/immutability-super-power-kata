package com.github.slaout.immutability.exercise1.usecase;

import com.github.slaout.immutability.exercise1.domain.edit.Edit;
import com.github.slaout.immutability.exercise1.domain.report.Price;
import com.github.slaout.immutability.exercise1.domain.report.PriceReport;
import com.github.slaout.immutability.exercise1.doubles.TestPriceReportRepository;
import com.github.slaout.immutability.exercise1.doubles.TestProductRepository;
import com.github.slaout.immutability.exercise1.doubles.TestSellerRepository;

import java.math.BigDecimal;

import static com.github.slaout.immutability.exercise1.fixture.ProductFixtures.KNOWN_PRODUCT;
import static com.github.slaout.immutability.exercise1.fixture.ReportFixtures.*;

public class TestBase {

    protected final TestPriceReportRepository priceReportRepository = new TestPriceReportRepository();
    protected final TestProductRepository productRepository = new TestProductRepository();
    protected final TestSellerRepository sellerRepository = new TestSellerRepository();

    protected void givenExistingReportForKnownProductAndSeller() {
        givenKnownProductAndSeller();
        priceReportRepository.setKnownReport(EXISTING_REPORT_FOR_KNOWN_PRODUCT_AND_SELLER);
    }

    protected void givenExistingReportForKnownProductAndSellerHaving(BigDecimal amount, Edit edit) {
        givenKnownProductAndSeller();
        priceReportRepository.setKnownReport(new PriceReport(
                KNOWN_PRODUCT,
                KNOWN_SELLER,
                Price.restoreFromDatabase(amount, edit, ANY_CURRENCY, edit)));
    }

    protected void givenKnownProductAndSeller() {
        givenKnownProduct();
        givenKnownSeller();
    }

    protected void givenKnownProduct() {
        productRepository.setKnownProduct(KNOWN_PRODUCT);
    }

    protected void givenKnownSeller() {
        sellerRepository.setKnownSeller(KNOWN_SELLER);
    }

}
