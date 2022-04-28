package com.github.slaout.immutability.exercise2.domain;

import com.github.slaout.immutability.exercise2.domain.Product;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class OrderLine {
    Product product;
    int quantity;
    BigDecimal price;
    List<Option> options;
}
