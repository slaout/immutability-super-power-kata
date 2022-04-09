package com.github.slaout.immutability.exercise1.usecase;

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
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class XlsxImportUseCase {

    private final PriceReportRepository priceReportRepository;
    private final CurrencyRepository currencyRepository;

    public List<PriceReport> importRows(List<ImportRow> rows, User connectedUser) {
        List<PriceReport> reports = priceReportRepository.getReports();
        List<Currency> availableCurrencies = currencyRepository.getCurrencies();

        rows.forEach(row -> importRow(reports, availableCurrencies, row, connectedUser));

        priceReportRepository.save(reports);

        return reports;
    }

    private void importRow(List<PriceReport> reports, List<Currency> availableCurrencies, ImportRow row, User connectedUser) {
        PriceReport report = reports.stream()
                .filter(r -> r.getProduct().getEan().equals(row.getProductEan()) &&
                        r.getSeller().getId() == row.getSellerId())
                .findFirst()
                .orElseThrow(UnknownReportException::new);

        Currency newCurrency = availableCurrencies.stream()
                .filter(c -> c.getCode().equals(row.getNewCurrencyCode()))
                .findFirst()
                .orElseThrow(UnknownCurrencyException::new);

        report.getPrice().setAmount(row.getNewAmount());

        report.getPrice().setCurrency(newCurrency);

        Edit lastEdit = report.getPrice().getLastAmountEdit();
        // TODO[TRUE STORY] BUG: Missing Action.IMPORT
        lastEdit.setInstant(Instant.now());
        lastEdit.setLogin(connectedUser.getLogin());
        lastEdit.setFullName(connectedUser.getFullName());
        // TODO[TRUE STORY] BUG: Missing lastCurrencyEdit
    }

    @Value
    public static class ImportRow {
        Ean productEan;
        long sellerId;
        BigDecimal newAmount;
        String newCurrencyCode;
    }

}
