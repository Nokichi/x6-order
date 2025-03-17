package ru.jabka.x6order.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabka.x6order.model.Order;
import ru.jabka.x6order.model.mapper.OrderMapper;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrderMapper orderMapper;

    private static final String INSERT = """
            INSERT INTO x6.order (user_id)
            VALUES(:user_id)
            RETURNING *
            """;

    public Order insert(Long userId) {
        return jdbcTemplate.queryForObject(INSERT, new MapSqlParameterSource("user_id", userId), orderMapper);
    }
}