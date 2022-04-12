package com.github.slaout.immutability.exercise2.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class OrderCsvRow {
    // Order
    long orderId;
    String userName;
    String address;
    // Order.line.product
    String productEan;
    String productName;
    // Order.line
    int quantity;
    BigDecimal price;
    // Order.line.options[i]
    String option;
}
