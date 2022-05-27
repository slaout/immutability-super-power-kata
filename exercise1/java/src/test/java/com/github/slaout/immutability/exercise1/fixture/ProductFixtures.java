package com.github.slaout.immutability.exercise1.fixture;

import com.github.slaout.immutability.exercise1.domain.product.Ean;
import com.github.slaout.immutability.exercise1.domain.product.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductFixtures {

    public static final Ean KNOWN_PRODUCT_EAN = new Ean("known");
    public static final Ean ANOTHER_KNOWN_PRODUCT_EAN = new Ean("another-known");
    public static final Ean UNKNOWN_EAN = new Ean("unknown");

    public static final Product KNOWN_PRODUCT = new Product(KNOWN_PRODUCT_EAN, "Any name");
    public static final Product ANOTHER_KNOWN_PRODUCT = new Product(ANOTHER_KNOWN_PRODUCT_EAN, "Any other name");

}
