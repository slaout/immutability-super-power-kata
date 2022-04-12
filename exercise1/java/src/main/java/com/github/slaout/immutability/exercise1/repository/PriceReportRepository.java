package com.github.slaout.immutability.exercise1.repository;

import com.github.slaout.immutability.exercise1.domain.product.Ean;
import com.github.slaout.immutability.exercise1.domain.report.PriceReport;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PriceReportRepository {

    List<PriceReport> getReports();

    Optional<PriceReport> getReport(Ean productEan, long sellerId);

    void save(PriceReport report);

    void save(Collection<PriceReport> reports);

}
