package ru.jabka.x6order.model;

import lombok.Builder;

import java.util.Set;

@Builder
public record OrderRequest(
        Long userId,
        Set<Product> products
) {
}