package com.github.slaout.immutability.exercise1.doubles;

import com.github.slaout.immutability.exercise1.domain.product.Ean;
import com.github.slaout.immutability.exercise1.domain.product.Product;
import com.github.slaout.immutability.exercise1.repository.ProductRepository;
import lombok.Setter;

import java.util.Optional;

public class TestProductRepository implements ProductRepository {

    @Setter
    private Product knownProduct;

    public Optional<Product> getProduct(Ean ean) {
        if (knownProduct != null && ean.equals(knownProduct.getEan())) {
            return Optional.of(knownProduct);
        } else {
            return Optional.empty();
        }
    }

}
