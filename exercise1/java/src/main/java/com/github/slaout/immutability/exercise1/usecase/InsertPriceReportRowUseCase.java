package com.github.slaout.immutability.exercise1.usecase;

import com.github.slaout.immutability.exercise1.domain.edit.Action;
import com.github.slaout.immutability.exercise1.domain.edit.Edit;
import com.github.slaout.immutability.exercise1.domain.edit.User;
import com.github.slaout.immutability.exercise1.domain.product.Ean;
import com.github.slaout.immutability.exercise1.domain.product.Product;
import com.github.slaout.immutability.exercise1.domain.report.Price;
import com.github.slaout.immutability.exercise1.domain.report.PriceReport;
import com.github.slaout.immutability.exercise1.domain.report.Seller;
import com.github.slaout.immutability.exercise1.exception.DuplicateRowException;
import com.github.slaout.immutability.exercise1.exception.UnknownProductException;
import com.github.slaout.immutability.exercise1.exception.UnknownSellerException;
import com.github.slaout.immutability.exercise1.repository.CurrencyRepository;
import com.github.slaout.immutability.exercise1.repository.PriceReportRepository;
import com.github.slaout.immutability.exercise1.repository.ProductRepository;
import com.github.slaout.immutability.exercise1.repository.SellerRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class InsertPriceReportRowUseCase {

    private final PriceReportRepository priceReportRepository;
    private final CurrencyRepository currencyRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    public PriceReport insertPriceReportRow(Ean productEan, long sellerId, User connectedUser) {
        if (priceReportRepository.getReport(productEan, sellerId).isPresent()) {
            throw new DuplicateRowException();
        }

        Product product = productRepository.getProduct(productEan).orElseThrow(UnknownProductException::new);
        Seller seller = sellerRepository.getSeller(sellerId).orElseThrow(UnknownSellerException::new);

        Edit creationEdit = new Edit();
        creationEdit.setAction(Action.CREATION);
        creationEdit.setInstant(Instant.now());
        creationEdit.setLogin(connectedUser.getLogin());
        creationEdit.setFullName(connectedUser.getFullName());

        Price price = Price.builder()
                .currency(currencyRepository.getDefaultCurrency())
                .lastCurrencyEdit(creationEdit)
                .build();

        PriceReport report = PriceReport.builder()
                .product(product)
                .seller(seller)
                .price(price)
                .build();

        priceReportRepository.save(report);

        return report;
    }

}
