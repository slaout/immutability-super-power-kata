package com.github.slaout.immutability.exercise1.usecase;

import com.github.slaout.immutability.exercise1.domain.edit.Action;
import com.github.slaout.immutability.exercise1.domain.edit.Edit;
import com.github.slaout.immutability.exercise1.domain.edit.User;
import com.github.slaout.immutability.exercise1.domain.report.Currency;
import com.github.slaout.immutability.exercise1.domain.report.PriceReport;
import com.github.slaout.immutability.exercise1.doubles.TestCurrencyRepository;
import com.github.slaout.immutability.exercise1.exception.DuplicateRowException;
import com.github.slaout.immutability.exercise1.exception.UnknownProductException;
import com.github.slaout.immutability.exercise1.exception.UnknownSellerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.github.slaout.immutability.exercise1.domain.support.TestSpaceTimeContinuum.givenClockIs;
import static com.github.slaout.immutability.exercise1.fixture.EditFixtures.*;
import static com.github.slaout.immutability.exercise1.fixture.ProductFixtures.*;
import static com.github.slaout.immutability.exercise1.fixture.ReportFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InsertPriceReportRowUseCaseTest extends TestBase {

    private final TestCurrencyRepository currencyRepository = new TestCurrencyRepository();

    private final InsertPriceReportRowUseCase cut;

    InsertPriceReportRowUseCaseTest() {
        cut = new InsertPriceReportRowUseCase(
                priceReportRepository,
                currencyRepository,
                productRepository,
                sellerRepository);
    }

    @Test
    void it_shouldThrowDuplicateRowException_whenInsertingADuplicateProductAndSellerPair() {
        // GIVEN
        givenExistingReportForKnownProductAndSeller();

        // WHEN
        Executable when = () ->
                cut.insertPriceReportRow(KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, anyUser());

        // THEN
        assertThrows(DuplicateRowException.class, when);
    }

    @Test
    void it_shouldThrowUnknownProductException_whenInsertingAnUnknownProductEan() {
        // GIVEN
        givenExistingReportForKnownProductAndSeller();

        // WHEN
        Executable when = () ->
                cut.insertPriceReportRow(UNKNOWN_EAN, KNOWN_SELLER_ID, anyUser());

        // THEN
        assertThrows(UnknownProductException.class, when);
    }

    @Test
    void it_shouldThrowUnknownSellerException_whenInsertingAnUnknownSellerId() {
        // GIVEN
        givenExistingReportForKnownProductAndSeller();

        // WHEN
        Executable when = () ->
                cut.insertPriceReportRow(KNOWN_PRODUCT_EAN, UNKNOWN_SELLER_ID, anyUser());

        // THEN
        assertThrows(UnknownSellerException.class, when);
    }

    @Test
    void it_shouldReportProductWithSeller_whenInsertingAnotherKnownEanWhileAReportExistsWithSameSellerId() {
        // GIVEN
        givenExistingReportForKnownProductAndSeller();
        productRepository.setKnownProduct(ANOTHER_KNOWN_PRODUCT);

        // WHEN
        cut.insertPriceReportRow(ANOTHER_KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, anyUser());

        // THEN
        PriceReport savedReport = priceReportRepository.getSavedReport();
        assertThat(savedReport.getProduct()).isEqualTo(ANOTHER_KNOWN_PRODUCT);
        assertThat(savedReport.getSeller()).isEqualTo(KNOWN_SELLER);
    }

    @Test
    void it_shouldReportProductWithSeller_whenInsertingAnotherKnownSellerIdWhileAReportExistsWithSameEan() {
        // GIVEN
        givenExistingReportForKnownProductAndSeller();
        sellerRepository.setKnownSeller(ANOTHER_KNOWN_SELLER);

        // WHEN
        cut.insertPriceReportRow(KNOWN_PRODUCT_EAN, ANOTHER_KNOWN_SELLER_ID, anyUser());

        // THEN
        PriceReport savedReport = priceReportRepository.getSavedReport();
        assertThat(savedReport.getProduct()).isEqualTo(KNOWN_PRODUCT);
        assertThat(savedReport.getSeller()).isEqualTo(ANOTHER_KNOWN_SELLER);
    }

    @Test
    void it_shouldHaveNoAmount_whenInsertingReport() {
        // GIVEN
        givenKnownProductAndSeller();

        // WHEN
        cut.insertPriceReportRow(KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, anyUser());

        // THEN
        PriceReport savedReport = priceReportRepository.getSavedReport();
        assertThat(savedReport.getPrice().getAmount().getValue()).isNull();
    }

    @Test
    void it_shouldUseDefaultCurrency_whenInsertingReport() {
        // GIVEN
        givenKnownProductAndSeller();
        currencyRepository.setDefaultCurrency(DEFAULT_CURRENCY);

        // WHEN
        cut.insertPriceReportRow(KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, anyUser());

        // THEN
        PriceReport savedReport = priceReportRepository.getSavedReport();
        Currency savedCurrency = savedReport.getPrice().getCurrency().getValue();
        assertThat(savedCurrency).isEqualTo(DEFAULT_CURRENCY);
    }

    @Test
    void it_shouldInitializeAmountEdit_whenInsertingReport() {
        // GIVEN
        givenClockIs(ALWAYS_NOW);
        givenKnownProductAndSeller();
        User user = new User("user login", "User Full Name");

        // WHEN
        cut.insertPriceReportRow(KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, user);

        // THEN
        PriceReport savedReport = priceReportRepository.getSavedReport();
        Edit savedAmountEdit = savedReport.getPrice().getAmount().getLastEdit();
        assertThat(savedAmountEdit.getUser()).isEqualTo(user);
        assertThat(savedAmountEdit.getInstant()).isEqualTo(NOW);
        assertThat(savedAmountEdit.getAction()).isEqualTo(Action.CREATION);
    }

    // TODO Split above and below methods into 3 each?
    // TODO Or Merge and assume its equals()

    @Test
    void it_shouldInitializeCurrencyEdit_whenInsertingReport() {
        // GIVEN
        givenClockIs(ALWAYS_NOW);
        givenKnownProductAndSeller();
        User user = new User("user login", "User Full Name");

        // WHEN
        cut.insertPriceReportRow(KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, user);

        // THEN
        PriceReport savedReport = priceReportRepository.getSavedReport();
        Edit savedCurrencyEdit = savedReport.getPrice().getCurrency().getLastEdit();
        assertThat(savedCurrencyEdit.getUser()).isEqualTo(user);
        assertThat(savedCurrencyEdit.getInstant()).isEqualTo(NOW);
        assertThat(savedCurrencyEdit.getAction()).isEqualTo(Action.CREATION);
    }

    // TODO it_shouldReturnTheInsertedRow_whenInserting

}
