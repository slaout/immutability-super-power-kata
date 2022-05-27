package com.github.slaout.immutability.exercise1.usecase;

import com.github.slaout.immutability.exercise1.domain.report.PriceReport;
import com.github.slaout.immutability.exercise1.exception.UnknownReportException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;

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
    void it_shouldThrowUnknownReportException_whenProductAndSellerPairIsUnknown() {
        // GIVEN
        givenExistingReportForKnownProductAndSeller();

        // WHEN
        Executable when = () ->
                cut.updateAmount(ANOTHER_KNOWN_PRODUCT_EAN, ANOTHER_KNOWN_SELLER_ID, ANY_AMOUNT, ANY_USER);

        // THEN
        assertThrows(UnknownReportException.class, when);
    }
    // TODO when one of the properties of the pair
    // TODO when the other of the properties of the pair

    @Test
    void it_shouldChangeAmount_whenSettingANewAmount() {
        // GIVEN
        givenExistingReportForKnownProductAndSeller();
        BigDecimal newAmount = new BigDecimal("20");

        // WHEN
        cut.updateAmount(KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, newAmount, ANY_USER);

        // THEN
        PriceReport savedReport = priceReportRepository.getSavedReport();
        assertThat(savedReport.getPrice().getAmount().getValue()).isEqualTo(newAmount);
    }

    @Test
    void it_shouldChangeAmountEdit_whenSettingANewAmount() {
        // GIVEN
        givenExistingReportForKnownProductAndSeller();
        BigDecimal newAmount = new BigDecimal("20");

        // WHEN
        cut.updateAmount(KNOWN_PRODUCT_EAN, KNOWN_SELLER_ID, newAmount, ANY_USER);

        // THEN
        PriceReport savedReport = priceReportRepository.getSavedReport();
        assertThat(savedReport.getPrice().getAmount().getValue()).isEqualTo(newAmount);
    }

    // TODO it_shouldKeepAmountEdit_whenSettingTheSameAmount

    // TODO it_shouldKeepCurrencyEdit_whenSettingANewAmount

    // TODO it_shouldReturnTheInsertedRow_whenInserting

    // TODO Try analyzing with PiTest

    // TODO Test equals()??? because other tests assume it works (and remove with records?)

}