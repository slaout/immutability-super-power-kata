package com.github.slaout.immutability.exercise1.repository;

import com.github.slaout.immutability.exercise1.domain.product.Ean;
import com.github.slaout.immutability.exercise1.domain.product.Product;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> getProduct(Ean ean);

}
