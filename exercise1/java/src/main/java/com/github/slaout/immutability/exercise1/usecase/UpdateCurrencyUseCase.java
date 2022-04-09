package com.github.slaout.immutability.exercise1.usecase;

import com.github.slaout.immutability.exercise1.domain.edit.Action;
import com.github.slaout.immutability.exercise1.domain.edit.Edit;
import com.github.slaout.immutability.exercise1.domain.edit.User;
import com.github.slaout.immutability.exercise1.domain.product.Ean;
import com.github.slaout.immutability.exercise1.domain.report.Currency;
import com.github.slaout.immutability.exercise1.domain.report.PriceReport;
import com.github.slaout.immutability.exercise1.exception.UnknownCurrencyException;
import com.github.slaout.immutability.exercise1.exception.UnknownReportException;
import com.github.slaout.immutability.exercise1.repository.CurrencyRepository;
import com.github.slaout.immutability.exercise1.repository.PriceReportRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class UpdateCurrencyUseCase {

    private final PriceReportRepository priceReportRepository;
    private final CurrencyRepository currencyRepository;

    public PriceReport updateCurrency(Ean productEan, long sellerId, String newCurrencyCode, User connectedUser) {
        PriceReport report = priceReportRepository.getReport(productEan, sellerId)
                .orElseThrow(UnknownReportException::new);

        Currency newCurrency = currencyRepository.getCurrency(newCurrencyCode)
                .orElseThrow(UnknownCurrencyException::new);

        report.getPrice().setCurrency(newCurrency);

        Edit lastEdit = new Edit();
        lastEdit.setAction(Action.EDITION);
        lastEdit.setInstant(Instant.now());
        lastEdit.setLogin(connectedUser.getLogin());
        lastEdit.setFullName(connectedUser.getFullName());
        report.getPrice().setLastAmountEdit(lastEdit); // TODO[TRUE STORY] BUG: Wrong edit

        priceReportRepository.save(report);

        return report;
    }

}
