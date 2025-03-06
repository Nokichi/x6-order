package ru.jabka.x6order.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.jabka.x6order.model.Exists;

@Component
@RequiredArgsConstructor
public class ProductClient {
    private final RestTemplate productService;

    public boolean isProductExists(Long userId) {
        return productService.getForObject("/api/v1/product/exists?id=" + userId, Exists.class).exists();
    }
}