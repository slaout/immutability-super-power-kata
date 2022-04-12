package com.github.slaout.immutability.exercise1.doubles;

import com.github.slaout.immutability.exercise1.domain.product.Ean;
import com.github.slaout.immutability.exercise1.domain.product.Product;
import com.github.slaout.immutability.exercise1.repository.ProductRepository;

import java.util.Optional;

public class TestProductRepository implements ProductRepository {

    public Optional<Product> getProduct(Ean ean) {
        return Optional.of(new Product(ean, "Some product"));
    }

}
