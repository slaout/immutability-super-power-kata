package com.github.slaout.immutability.exercise1.doubles;

import com.github.slaout.immutability.exercise1.domain.product.Ean;
import com.github.slaout.immutability.exercise1.domain.report.PriceReport;
import com.github.slaout.immutability.exercise1.repository.PriceReportRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class TestPriceReportRepository implements PriceReportRepository {

    @Getter
    @Setter
    private PriceReport knownReport;

    @Getter
    private PriceReport savedReport;

    public List<PriceReport> getReports() {
        return knownReport == null ? List.of() : List.of(knownReport);
//        Product titanicCd = new Product(new Ean("1234567890123"), "CD Titanic");
//
//        Seller auchanV2 = new Seller(2, "Auchan Villeneuve D'Ascq V2");
//        Seller alliExpressCom = new Seller(8, "AliExpress.com");
//
//        Currency euro = new Currency("EUR", BigDecimal.ONE);
//        Currency yuan = new Currency("CNY", new BigDecimal("0.14"));
//
//        User user = new User("someone@gmail.com", "Someone");
//        Edit edit1 = Edit.now(user, Action.CREATION);
//
//        return List.of(
//                new PriceReport(titanicCd, auchanV2, Price.restoreFromDatabase(new BigDecimal("14.99"), edit1, euro, edit1)),
//                new PriceReport(titanicCd, alliExpressCom, Price.restoreFromDatabase(new BigDecimal("9.99"), edit1, yuan, edit1)));
    }

    public Optional<PriceReport> getReport(Ean productEan, long sellerId) {
        List<PriceReport> reports = getReports();
        return reports.stream().filter(report ->
                        report.getProduct().getEan().equals(productEan) &&
                                report.getSeller().getId() == sellerId)
                .findFirst();
    }

    public void save(PriceReport report) {
        savedReport = report;
    }

    public void save(Collection<PriceReport> reports) {
        System.out.println("Saved " + reports);
    }

}
