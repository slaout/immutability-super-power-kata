package com.github.slaout.immutability.exercise1.doubles;

import com.github.slaout.immutability.exercise1.domain.edit.Action;
import com.github.slaout.immutability.exercise1.domain.edit.Edit;
import com.github.slaout.immutability.exercise1.domain.product.Ean;
import com.github.slaout.immutability.exercise1.domain.product.Product;
import com.github.slaout.immutability.exercise1.domain.report.Currency;
import com.github.slaout.immutability.exercise1.domain.report.Price;
import com.github.slaout.immutability.exercise1.domain.report.PriceReport;
import com.github.slaout.immutability.exercise1.domain.report.Seller;
import com.github.slaout.immutability.exercise1.repository.PriceReportRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class TestPriceReportRepository implements PriceReportRepository {

    public List<PriceReport> getReports() {
        Product titanicCd = new Product(new Ean("1234567890123"), "CD Titanic");

        Seller auchanV2 = new Seller(2, "Auchan Villeneuve D'Ascq V2");
        Seller alliExpressCom = new Seller(8, "AliExpress.com");

        Currency euro = new Currency("EUR", BigDecimal.ONE);
        Currency yuan = new Currency("CNY", new BigDecimal("0.14"));

        Edit edit1 = new Edit(Instant.now(), Action.CREATION);
        edit1.setLogin("someone@gmail.com");
        edit1.setFullName("Someone");

        return List.of(
                new PriceReport(titanicCd, auchanV2, new Price(new BigDecimal("14.99"), edit1, euro, edit1)),
                new PriceReport(titanicCd, alliExpressCom, new Price(new BigDecimal("9.99"), edit1, yuan, edit1)));
    }

    public Optional<PriceReport> getReport(Ean productEan, long sellerId) {
        List<PriceReport> reports = getReports();
        return reports.stream().filter(report ->
                        report.getProduct().getEan().equals(productEan) &&
                                report.getSeller().getId() == sellerId)
                .findFirst();
    }

    public void save(PriceReport report) {
        System.out.println("Saved " + report);
    }

    public void save(Collection<PriceReport> reports) {
        System.out.println("Saved " + reports);
    }

}
