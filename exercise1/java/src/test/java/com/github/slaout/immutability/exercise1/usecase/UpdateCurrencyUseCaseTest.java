package com.github.slaout.immutability.exercise1.usecase;

import com.github.slaout.immutability.exercise1.domain.edit.Action;
import com.github.slaout.immutability.exercise1.domain.edit.Edit;
import com.github.slaout.immutability.exercise1.domain.edit.User;
import com.github.slaout.immutability.exercise1.domain.report.Currency;
import com.github.slaout.immutability.exercise1.domain.report.PriceReport;
import com.github.slaout.immutability.exercise1.doubles.TestCurrencyRepository;
import com.github.slaout.immutability.exercise1.exception.UnknownCurrencyException;
import com.github.slaout.immutability.exercise1.exception.UnknownReportException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.github.slaout.immutability.exercise1.domain.support.TestSpaceTimeContinuum.givenClockIs;
import static com.github.slaout.immutability.exercise1.fixture.EditFixtures.*;
import static com.github.slaout.immutability.exercise1.fixture.ProductFixtures.ANOTHER_KNOWN_PRODUCT_EAN;
import static com.github.slaout.immutability.exercise1.fixture.ProductFixtures.KNOWN_PRODUCT_EAN;
import static com.github.slaout.immutability.exercise1.fixture.ReportFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UpdateCurrencyUseCaseTest extends TestBase {

    private final TestCurrencyRepository currencyRepository = new TestCurrencyRepository();

    private final UpdateCurrencyUseCase cut;

    UpdateCurrencyUseCaseTest() {
        cut = new UpdateCurrencyUseCase(priceReportRepository, currencyRepository);
    }

    @Test
    void it_shouldThrowUnknownReportException_whenProductAndSellerPairHasNoReport() {
        // GIVEN
        givenExistingReportForKnownProductAndSeller();

        // WHEN
        Executable when = () ->
                cut.updateCurrency(ANOTHER_KNOWN_PRODUCT_EAN, ANOTHER_KNOWN_SELLER_ID, ANY_CURRENCY_CODE, anyUser());

        // THEN
        assertThrows(UnknownReportException.class, when);
    }

    @Test
    void it_shouldThrowUnknownReportException_whenProductHasNoReportWithTheSeller() {
        // GIVEN
        givenExistingReportForKnownProductAndSeller();

        // WHEN
        Executable when = () ->
                cut.updateCurrency(KNOWN_PRODUCT_EAN, ANOTHER_KNOWN_SELLER_ID, ANY_CURRENCY_CODE, anyUser());

        // THEN
        assertThrows(UnknownReportException.class, when);
    }

    @Test
    void it_shouldThrowUnknownReportException_whenSellerHasNoReportWithTheProduct() {
        // GIVEN
        givenExistingReportForKnownProductAndSeller();

        // WHEN
        Executable when = () ->
                cut.updateCurrency(ANOTHER_KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, ANY_CURRENCY_CODE, anyUser());

        // THEN
        assertThrows(UnknownReportException.class, when);
    }

    @Test
    void it_shouldThrowUnknownCurrencyException_whenNewCurrencyDoesNotExist() {
        // GIVEN
        givenExistingReportForKnownProductAndSeller();

        // WHEN
        Executable when = () ->
                cut.updateCurrency(KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, UNKNOWN_CURRENCY_CODE, anyUser());

        // THEN
        assertThrows(UnknownCurrencyException.class, when);
    }

    @Test
    void it_shouldChangeCurrencyCode_whenSettingANewCurrency() {
        // GIVEN
        String newCurrencyCode = "NEW";
        givenKnownOtherCurrencyCode(newCurrencyCode); // TODO rename AnotherKnown
        givenExistingReportForKnownProductAndSeller();

        // WHEN
        cut.updateCurrency(KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, newCurrencyCode, anyUser());

        // THEN
        PriceReport savedReport = priceReportRepository.getSavedReport();
        assertThat(savedReport.getPrice().getCurrency().getValue().getCode()).isEqualTo(newCurrencyCode);
    }

    // TODO it_shouldRefreshCurrencyExchangeRate_whenSettingANewCurrency

    @Test
    void it_shouldChangeCurrencyEdit_whenSettingANewCurrency() {
        // GIVEN
        String newCurrencyCode = "NEW";
        givenKnownOtherCurrencyCode(newCurrencyCode);
        givenExistingReportForKnownProductAndSeller();
        User user = new User("editor-login", "Editor Full Name");
        givenClockIs(ALWAYS_NOW);

        // WHEN
        cut.updateCurrency(KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, newCurrencyCode, user);

        // THEN
        PriceReport savedReport = priceReportRepository.getSavedReport();
        Edit savedCurrencyEdit = savedReport.getPrice().getCurrency().getLastEdit();
        assertThat(savedCurrencyEdit.getUser()).isEqualTo(user); // TODO Check login & fullName
        assertThat(savedCurrencyEdit.getInstant()).isEqualTo(NOW);
        assertThat(savedCurrencyEdit.getAction()).isEqualTo(Action.EDITION);
    }

    @Test
    void it_shouldKeepCurrencyEdit_whenSettingTheSameCurrency() {
        // GIVEN
        String currentCurrencyCode = "CURRENT";
        givenKnownOtherCurrencyCode(currentCurrencyCode);

        Edit previousEdit = SOME_EDIT;
        givenExistingReportForKnownProductAndSellerHaving(currentCurrencyCode, previousEdit);

        // WHEN
        cut.updateCurrency(KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, currentCurrencyCode, anyUser());

        // THEN
        PriceReport savedReport = priceReportRepository.getSavedReport();
        assertThat(savedReport.getPrice().getCurrency().getLastEdit()).isEqualTo(previousEdit);
    }

    @Test
    void it_shouldKeepAmountEdit_whenSettingANewCurrency() {
        // GIVEN
        String newCurrencyCode = "NEW";
        givenKnownOtherCurrencyCode(newCurrencyCode);

        Edit previousEdit = SOME_EDIT;
        givenExistingReportForKnownProductAndSellerHaving(ANY_CURRENCY_CODE, previousEdit);

        // WHEN
        cut.updateCurrency(KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, newCurrencyCode, anyUser());

        // THEN
        PriceReport savedReport = priceReportRepository.getSavedReport();
        assertThat(savedReport.getPrice().getAmount().getLastEdit()).isEqualTo(previousEdit);
    }

    @Test
    void it_shouldReturnTheInsertedRow_whenInserting() {
        // GIVEN
        String newCurrencyCode = "NEW";
        givenKnownOtherCurrencyCode(newCurrencyCode);
        givenExistingReportForKnownProductAndSeller();

        // WHEN
        PriceReport returnedReport = cut.updateCurrency(KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, newCurrencyCode, anyUser());

        // THEN
        PriceReport savedReport = priceReportRepository.getSavedReport();
        assertThat(returnedReport).isEqualTo(savedReport);
    }

    // TODO MOVE IN BASE?
    private void givenKnownOtherCurrencyCode(String currencyCode) {
        currencyRepository.setKnownOtherCurrency(new Currency(currencyCode, ANY_EXCHANGE_RATE_TO_EURO));
    }

    // TODO Try analyzing with PiTest

    // TODO Test equals()??? because other tests assume it works (and remove with records?)

}