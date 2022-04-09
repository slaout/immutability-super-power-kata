package com.github.slaout.immutability.exercise1.domain.report;

import com.github.slaout.immutability.exercise1.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceReport {
    private final Product product;
    private final Seller seller;
    private Price price;
}
