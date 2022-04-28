package com.github.slaout.immutability.exercise2.usecase;

import com.github.slaout.immutability.exercise2.domain.Ean;
import com.github.slaout.immutability.exercise2.domain.Product;
import com.github.slaout.immutability.exercise2.domain.Option;
import com.github.slaout.immutability.exercise2.domain.Order;
import com.github.slaout.immutability.exercise2.domain.OrderLine;
import com.github.slaout.immutability.exercise2.dto.OrderCsvRow;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class FlatOrderImportUseCase {

    public List<Order> importFromParsedCsvRows(List<OrderCsvRow> rows) {
        return groupRowsByOrder(rows).stream()
                .map(this::toOrder)
                .collect(toList());
    }

    private Collection<List<OrderCsvRow>> groupRowsByOrder(List<OrderCsvRow> rows) {
        return rows.stream()
                .collect(groupingBy(OrderCsvRow::getOrderId))
                .values();
    }

    private Order toOrder(List<OrderCsvRow> rowsOfOneOrder) {
        OrderCsvRow orderRow = rowsOfOneOrder.get(0);
        return new Order(
                orderRow.getOrderId(),
                orderRow.getUserName(),
                orderRow.getAddress(),
                toOrderLines(rowsOfOneOrder));
    }

    private List<OrderLine> toOrderLines(List<OrderCsvRow> rowsOfOneOrder) {
        return groupRowsByLinesOfOneOrder(rowsOfOneOrder).stream()
                .map(this::toOrderLine)
                .collect(toList());
    }

    private Collection<List<OrderCsvRow>> groupRowsByLinesOfOneOrder(List<OrderCsvRow> rowsOfOneOrder) {
        return rowsOfOneOrder.stream()
                .collect(groupingBy(OrderCsvRow::getProductEan))
                .values();
    }

    private OrderLine toOrderLine(List<OrderCsvRow> rowsOfOneOrderLine) {
        OrderCsvRow orderLineRow = rowsOfOneOrderLine.get(0);
        return new OrderLine(
                new Product(new Ean(orderLineRow.getProductEan()), orderLineRow.getProductName()),
                orderLineRow.getQuantity(),
                orderLineRow.getPrice(),
                toOptions(rowsOfOneOrderLine));
    }

    private List<Option> toOptions(List<OrderCsvRow> rowsOfOneOrderLine) {
        return rowsOfOneOrderLine.stream()
                .filter(row -> row.getOption() != null)
                .map(row -> new Option(row.getOption()))
                .collect(toList());
    }

}
