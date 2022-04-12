package com.github.slaout.immutability.exercise2.domain;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private long id;
    private String userName;
    private String address;
    private List<OrderLine> lines;
}
