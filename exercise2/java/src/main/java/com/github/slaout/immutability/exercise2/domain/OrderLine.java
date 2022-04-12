package com.github.slaout.immutability.exercise2.domain;

import com.github.slaout.immutability.exercise2.domain.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderLine {
    private Product product;
    private int quantity;
    private BigDecimal price;
    private List<Option> options;
}
