package com.github.slaout.immutability.exercise1.usecase;

import com.github.slaout.immutability.exercise1.domain.report.Currency;
import com.github.slaout.immutability.exercise1.domain.report.Price;
import com.github.slaout.immutability.exercise1.domain.report.PriceReport;
import com.github.slaout.immutability.exercise1.repository.CurrencyRepository;
import com.github.slaout.immutability.exercise1.repository.PriceReportRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor
public class GetPriceReportsUseCase {

    private final PriceReportRepository priceReportRepository;
    private final CurrencyRepository currencyRepository;

    public List<PriceReport> getPriceReports() {
        List<PriceReport> reports = priceReportRepository.getReports();
        List<Currency> currencies = currencyRepository.getCurrencies();
        synchronizeExchangeRates(reports, currencies);
        return reports;
    }

    private void synchronizeExchangeRates(List<PriceReport> reports, List<Currency> currencies) {
        Map<String, BigDecimal> exchangeRates = currencies.stream()
                .collect(toMap(Currency::getCode, Currency::getExchangeRateToEuro));

        for (PriceReport report : reports) {
            Currency currency = report.getPrice().getCurrency();
            report.setPrice(new Price(
                    report.getPrice().getAmount(),
                    report.getPrice().getLastAmountEdit(),
                    new Currency(currency.getCode(), exchangeRates.get(currency.getCode())),
                    report.getPrice().getLastCurrencyEdit()));
            // TODO with*() or static factory
        }
    }

}
