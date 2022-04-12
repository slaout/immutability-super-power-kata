package com.github.slaout.immutability.exercise2.usecase;

import com.github.slaout.immutability.exercise2.domain.Option;
import com.github.slaout.immutability.exercise2.domain.Order;
import com.github.slaout.immutability.exercise2.domain.OrderLine;
import com.github.slaout.immutability.exercise2.dto.OrderCsvRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class FlatOrderImportUseCaseTest {

    private static final BigDecimal PRICE_1 = BigDecimal.valueOf(11);
    private static final BigDecimal PRICE_2 = BigDecimal.valueOf(12);
    private static final BigDecimal PRICE_3 = BigDecimal.valueOf(13);

    private final FlatOrderImportUseCase cut = new FlatOrderImportUseCase();

    @Test
    void importFromParsedCsvRows_shouldCreateAnObjectHierarchy_whenImportingFlatRows() {
        // GIVEN
        List<OrderCsvRow> order1Rows = List.of(
                // Product 1
                new OrderCsvRow(1, "user1", "address1", "ean1.1", "product1", 3, PRICE_1, "GoldWarranty"),
                new OrderCsvRow(1, "user1", "address1", "ean1.1", "product1", 3, PRICE_1, "PlatinumWarranty"),
                // Product 2
                new OrderCsvRow(1, "user1", "address1", "ean1.2", "product2", 4, PRICE_2, null));
        List<OrderCsvRow> order2Rows = List.of(
                // Product 3
                new OrderCsvRow(2, "user2", "address2", "ean2.3", "product3", 5, PRICE_3, "DLC1"),
                new OrderCsvRow(2, "user2", "address2", "ean2.3", "product3", 5, PRICE_3, "DLC2"));
        List<OrderCsvRow> rows = shuffle(union(order1Rows, order2Rows));

        // WHEN
        List<Order> orders = cut.importFromParsedCsvRows(rows);

        // THEN
        assertThat(orders).hasSize(2);
        {
            Order order1 = getById(orders, 1);
            assertThat(order1.getUserName()).isEqualTo("user1");
            assertThat(order1.getAddress()).isEqualTo("address1");
            {
                OrderLine order1Line1 = getByEan(order1.getLines(), "ean1.1");
                assertThat(order1Line1.getProduct().getName()).isEqualTo("product1");
                assertThat(order1Line1.getQuantity()).isEqualTo(3);
                assertThat(order1Line1.getPrice()).isEqualTo(PRICE_1);
                assertThat(order1Line1.getOptions()).containsExactlyInAnyOrder(new Option("GoldWarranty"), new Option("PlatinumWarranty"));
            }
            {
                OrderLine order1Line2 = getByEan(order1.getLines(), "ean1.2");
                assertThat(order1Line2.getProduct().getName()).isEqualTo("product2");
                assertThat(order1Line2.getQuantity()).isEqualTo(4);
                assertThat(order1Line2.getPrice()).isEqualTo(PRICE_2);
                assertThat(order1Line2.getOptions()).isEmpty();
            }
        }
        {
            Order order2 = getById(orders, 2);
            assertThat(order2.getUserName()).isEqualTo("user2");
            assertThat(order2.getAddress()).isEqualTo("address2");
            {
                OrderLine order2Line1 = getByEan(order2.getLines(), "ean2.3");
                assertThat(order2Line1.getProduct().getName()).isEqualTo("product3");
                assertThat(order2Line1.getQuantity()).isEqualTo(5);
                assertThat(order2Line1.getPrice()).isEqualTo(PRICE_3);
                assertThat(order2Line1.getOptions()).containsExactlyInAnyOrder(new Option("DLC1"), new Option("DLC2"));
            }
        }
    }

    private Order getById(List<Order> orders, long id) {
        return orders.stream()
                .filter(order -> order.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private OrderLine getByEan(List<OrderLine> orderLines, String ean) {
        return orderLines.stream()
                .filter(orderLine -> orderLine.getProduct().getEan().getCode().equals(ean))
                .findFirst()
                .orElse(null);
    }

    private <T> List<T> shuffle(List<T> input) {
        List<T> output = new ArrayList<>(input);
        Collections.shuffle(output);
        return output;
    }

    private <T> List<T> union(Collection<T> elements1, Collection<T> elements2) {
        return Stream
                .concat(elements1.stream(), elements2.stream())
                .collect(toList());
    }

}
