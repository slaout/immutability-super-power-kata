package com.github.slaout.immutability.exercise1.usecase;

import com.github.slaout.immutability.exercise1.domain.edit.Action;
import com.github.slaout.immutability.exercise1.domain.edit.Edit;
import com.github.slaout.immutability.exercise1.domain.edit.User;
import com.github.slaout.immutability.exercise1.domain.report.PriceReport;
import com.github.slaout.immutability.exercise1.exception.UnknownReportException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;

import static com.github.slaout.immutability.exercise1.domain.support.TestSpaceTimeContinuum.givenClockIs;
import static com.github.slaout.immutability.exercise1.fixture.EditFixtures.*;
import static com.github.slaout.immutability.exercise1.fixture.ProductFixtures.*;
import static com.github.slaout.immutability.exercise1.fixture.ReportFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UpdateAmountUseCaseTest extends TestBase {

    private final UpdateAmountUseCase cut;

    UpdateAmountUseCaseTest() {
        cut = new UpdateAmountUseCase(priceReportRepository);
    }

    @Test
    void it_shouldThrowUnknownReportException_whenProductAndSellerPairHasNoReport() {
        // GIVEN
        givenExistingReportForKnownProductAndSeller();

        // WHEN
        Executable when = () ->
                cut.updateAmount(ANOTHER_KNOWN_PRODUCT_EAN, ANOTHER_KNOWN_SELLER_ID, ANY_AMOUNT, anyUser());

        // THEN
        assertThrows(UnknownReportException.class, when);
    }

    @Test
    void it_shouldThrowUnknownReportException_whenProductHasNoReportWithTheSeller() {
        // GIVEN
        givenExistingReportForKnownProductAndSeller();

        // WHEN
        Executable when = () ->
                cut.updateAmount(KNOWN_PRODUCT_EAN, ANOTHER_KNOWN_SELLER_ID, ANY_AMOUNT, anyUser());

        // THEN
        assertThrows(UnknownReportException.class, when);
    }

    @Test
    void it_shouldThrowUnknownReportException_whenSellerHasNoReportWithTheProduct() {
        // GIVEN
        givenExistingReportForKnownProductAndSeller();

        // WHEN
        Executable when = () ->
                cut.updateAmount(ANOTHER_KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, ANY_AMOUNT, anyUser());

        // THEN
        assertThrows(UnknownReportException.class, when);
    }

    @Test
    void it_shouldChangeAmount_whenSettingANewAmount() {
        // GIVEN
        givenExistingReportForKnownProductAndSeller();
        BigDecimal newAmount = new BigDecimal("20");

        // WHEN
        cut.updateAmount(KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, newAmount, anyUser());

        // THEN
        PriceReport savedReport = priceReportRepository.getSavedReport();
        assertThat(savedReport.getPrice().getAmount().getValue()).isEqualTo(newAmount);
    }

    @Test
    void it_shouldChangeAmountEdit_whenSettingANewAmount() {
        // GIVEN
        givenExistingReportForKnownProductAndSeller();
        BigDecimal newAmount = new BigDecimal("20");
        User user = new User("editor-login", "Editor Full Name");
        givenClockIs(ALWAYS_NOW);

        // WHEN
        cut.updateAmount(KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, newAmount, user);

        // THEN
        PriceReport savedReport = priceReportRepository.getSavedReport();
        Edit savedAmountEdit = savedReport.getPrice().getAmount().getLastEdit();
        assertThat(savedAmountEdit.getUser()).isEqualTo(user);
        assertThat(savedAmountEdit.getInstant()).isEqualTo(NOW);
        assertThat(savedAmountEdit.getAction()).isEqualTo(Action.EDITION);
    }

    @Test
    void it_shouldKeepAmountEdit_whenSettingTheSameAmount() {
        // GIVEN
        BigDecimal currentAmount = new BigDecimal("20");
        Edit previousEdit = SOME_EDIT;
        givenExistingReportForKnownProductAndSellerHaving(currentAmount, previousEdit);

        // WHEN
        cut.updateAmount(KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, currentAmount, anyUser());

        // THEN
        PriceReport savedReport = priceReportRepository.getSavedReport();
        assertThat(savedReport.getPrice().getAmount().getLastEdit()).isEqualTo(previousEdit); // TODO Be careful: in previous code, User was mutable so the method could mutate the expected value :-/
    }

    @Test
    void it_shouldKeepCurrencyEdit_whenSettingANewAmount() {
        // GIVEN
        BigDecimal newAmount = new BigDecimal("20");
        Edit previousEdit = SOME_EDIT;
        givenExistingReportForKnownProductAndSellerHaving(ANY_AMOUNT, previousEdit);

        // WHEN
        cut.updateAmount(KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, newAmount, anyUser());

        // THEN
        PriceReport savedReport = priceReportRepository.getSavedReport();
        assertThat(savedReport.getPrice().getCurrency().getLastEdit()).isEqualTo(previousEdit);
    }

    @Test
    void it_shouldReturnTheInsertedRow_whenInserting() {
        // GIVEN
        givenExistingReportForKnownProductAndSeller();
        BigDecimal newAmount = new BigDecimal("20");

        // WHEN
        PriceReport returnedReport = cut.updateAmount(KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, newAmount, anyUser());

        // THEN
        PriceReport savedReport = priceReportRepository.getSavedReport();
        assertThat(returnedReport).isEqualTo(savedReport);
    }

    // TODO Try analyzing with PiTest

    // TODO Test equals()??? because other tests assume it works (and remove with records?)

}