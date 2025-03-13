package ru.jabka.x6order.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.jabka.x6order.model.ProductExists;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductClient {

    private final RestTemplate productService;

    public ProductExists isProductExists(Set<Long> productIds) {
        String queryParams = productIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("&ids="));
        return productService.getForObject("/api/v1/product/exists?ids=" + queryParams, ProductExists.class);
    }
}