package ru.jabka.x6order.model;

import lombok.Builder;

@Builder
public record Product(
        Long productId,
        Integer count
) {
}