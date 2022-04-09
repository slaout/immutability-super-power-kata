package com.github.slaout.immutability.exercise1.usecase;

import com.github.slaout.immutability.exercise1.domain.edit.Action;
import com.github.slaout.immutability.exercise1.domain.edit.Edit;
import com.github.slaout.immutability.exercise1.domain.edit.User;
import com.github.slaout.immutability.exercise1.domain.product.Ean;
import com.github.slaout.immutability.exercise1.domain.report.Currency;
import com.github.slaout.immutability.exercise1.domain.report.Price;
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

        report.setPrice(new Price(
                report.getPrice().getAmount(),
                report.getPrice().getLastAmountEdit(),
                newCurrency,
                new Edit(connectedUser, Instant.now(), Action.EDITION)));
        // TODO with*() or static factory

        priceReportRepository.save(report);

        return report;
    }

}
