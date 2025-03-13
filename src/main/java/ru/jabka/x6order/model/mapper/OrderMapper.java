package ru.jabka.x6order.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.jabka.x6order.model.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Component
public class OrderMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Order.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .createdAt(rs.getObject("created_at", Timestamp.class).toLocalDateTime())
                .updatedAt(rs.getObject("updated_at", Timestamp.class).toLocalDateTime())
                .build();
    }
}