package ru.jabka.x6order.model;

import lombok.Builder;

@Builder
public record ServiceError(Boolean success, String message) {
}