package ru.jabka.x6order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jabka.x6order.client.ProductClient;
import ru.jabka.x6order.exception.BadRequestException;
import ru.jabka.x6order.model.Product;
import ru.jabka.x6order.repository.CartRepository;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductClient productClient;

    @Transactional(rollbackFor = Throwable.class)
    public void addProducts(final Long orderId, final Set<Product> products) {
        validateOrder(orderId, products);
        cartRepository.insert(orderId, products);
    }

    private void validateOrder(final Long orderId, final Set<Product> products) {
        ofNullable(orderId).orElseThrow(() -> new BadRequestException("Заполните id заказа"));
        Map<Long, Boolean> productChecklist = productClient.isProductExists(products.stream()
                .map(Product::productId)
                .collect(Collectors.toSet())
        ).productChecklist();
        for (Product item : products) {
            if (!productChecklist.get(item.productId())) {
                throw new BadRequestException(String.format("Продукт с id %d не найден!", item.productId()));
            }
            if (item.count() < 1) {
                throw new BadRequestException("Количество продукта указано неверно. Минимальное значение = 1");
            }
        }
    }
}