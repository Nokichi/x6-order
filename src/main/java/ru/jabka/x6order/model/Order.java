package ru.jabka.x6order.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Order(
        Long id,
        Long userId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}