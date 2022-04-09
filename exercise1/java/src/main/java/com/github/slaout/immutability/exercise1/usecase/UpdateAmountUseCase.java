package com.github.slaout.immutability.exercise1.usecase;

import com.github.slaout.immutability.exercise1.domain.edit.Action;
import com.github.slaout.immutability.exercise1.domain.edit.Edit;
import com.github.slaout.immutability.exercise1.domain.edit.User;
import com.github.slaout.immutability.exercise1.domain.product.Ean;
import com.github.slaout.immutability.exercise1.domain.report.PriceReport;
import com.github.slaout.immutability.exercise1.exception.UnknownReportException;
import com.github.slaout.immutability.exercise1.repository.PriceReportRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class UpdateAmountUseCase {

    private final PriceReportRepository priceReportRepository;

    public PriceReport updateAmount(Ean productEan, long sellerId, BigDecimal newAmount, User connectedUser) {
        PriceReport report = priceReportRepository.getReport(productEan, sellerId)
                .orElseThrow(UnknownReportException::new);

        report.setPrice(report.getPrice().withEditedAmount(
                newAmount,
                Edit.now(connectedUser, Action.EDITION)));

        priceReportRepository.save(report);

        return report;
    }

}
