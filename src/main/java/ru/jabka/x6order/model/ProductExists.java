package ru.jabka.x6order.model;

import java.io.Serializable;
import java.util.Map;

public record ProductExists(
        Map<Long, Boolean> productChecklist
) implements Serializable {
}