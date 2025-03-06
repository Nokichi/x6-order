package ru.jabka.x6order.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabka.x6order.model.Product;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class CartRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT = """
            INSERT INTO x6.cart (order_id, product_id, count)
            VALUES (?, ?, ?)
            """;

    public void insert(Long orderId, Set<Product> productList) {
        jdbcTemplate.batchUpdate(INSERT, productList, productList.size(), ((ps, product) -> {
            ps.setLong(1, orderId);
            ps.setLong(2, product.productId());
            ps.setLong(3, product.count());
        }));
    }
}