package com.github.slaout.immutability.exercise2.usecase;

import com.github.slaout.immutability.exercise2.domain.Ean;
import com.github.slaout.immutability.exercise2.domain.Option;
import com.github.slaout.immutability.exercise2.domain.Order;
import com.github.slaout.immutability.exercise2.domain.OrderLine;
import com.github.slaout.immutability.exercise2.domain.Product;
import com.github.slaout.immutability.exercise2.dto.OrderCsvRow;

import java.util.ArrayList;
import java.util.List;

public class FlatOrderImportUseCase {

    public List<Order> importFromParsedCsvRows(List<OrderCsvRow> rows) {
        List<Order> orders = new ArrayList<>();

        for (OrderCsvRow row : rows) {
            Order order = getOrder(orders, row.getOrderId());
            if (order == null) {
                order = new Order();
                order.setId(row.getOrderId());
                order.setUserName(row.getUserName());
                order.setAddress(row.getAddress());
                order.setLines(new ArrayList<>());
                orders.add(order);
            }

            OrderLine orderLine = getOrderLine(order.getLines(), row.getProductEan());
            if (orderLine == null) {
                orderLine = new OrderLine();
                orderLine.setProduct(new Product(new Ean(row.getProductEan()), row.getProductName()));
                orderLine.setQuantity(row.getQuantity());
                orderLine.setPrice(row.getPrice());
                orderLine.setOptions(new ArrayList<>());
                order.getLines().add(orderLine);
            }

            if (row.getOption() != null) {
                orderLine.getOptions().add(new Option(row.getOption()));
            }
        }

        return orders;
    }

    private Order getOrder(List<Order> orders, long orderId) {
        return orders.stream()
                .filter(order -> order.getId() == orderId)
                .findFirst()
                .orElse(null);
    }

    private OrderLine getOrderLine(List<OrderLine> orderLines, String productEan) {
        return orderLines.stream()
                .filter(orderLine -> orderLine.getProduct().getEan().getCode().equals(productEan))
                .findFirst()
                .orElse(null);
    }

}
