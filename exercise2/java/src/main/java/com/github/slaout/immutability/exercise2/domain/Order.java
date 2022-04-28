package com.github.slaout.immutability.exercise2.domain;

import lombok.Value;

import java.util.List;

@Value
public class Order {
    long id;
    String userName;
    String address;
    List<OrderLine> lines;
}
