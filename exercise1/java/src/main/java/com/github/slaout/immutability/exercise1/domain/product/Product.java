package com.github.slaout.immutability.exercise1.domain.product;

import lombok.Value;

@Value
public class Product {
    Ean ean;
    String name;
}
