package com.github.slaout.immutability.exercise2.domain;

import lombok.Value;

@Value
public class Product {
    Ean ean;
    String name;
}
