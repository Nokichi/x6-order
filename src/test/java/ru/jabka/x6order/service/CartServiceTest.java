package ru.jabka.x6order.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.x6order.client.ProductClient;
import ru.jabka.x6order.exception.BadRequestException;
import ru.jabka.x6order.model.Product;
import ru.jabka.x6order.repository.CartRepository;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private CartService cartService;

    @Test
    void addProducts_success() {
        Long orderId = 1L;
        Set<Product> validProducts = getValidProducts();
        validProducts.forEach(product ->
                Mockito.when(productClient.isProductExists(product.productId())).thenReturn(true));
        cartService.addProducts(orderId, validProducts);
        Mockito.verify(cartRepository).insert(orderId, validProducts);
    }

    @Test
    void addProducts_error_nullOrderId() {
        Set<Product> validProducts = getValidProducts();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> cartService.addProducts(null, validProducts)
        );
        Assertions.assertEquals(exception.getMessage(), "Заполните id заказа");
        Mockito.verify(cartRepository, Mockito.never()).insert(Mockito.any(), Mockito.anySet());
    }

    @Test
    void addProducts_error_invalidCount() {
        Long orderId = 1L;
        Set<Product> products = Set.of(
                Product.builder()
                        .productId(1L)
                        .count(3)
                        .build(),
                Product.builder()
                        .productId(2L)
                        .count(0)
                        .build()
        );
        products.forEach(product ->
                Mockito.when(productClient.isProductExists(product.productId())).thenReturn(true));
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> cartService.addProducts(orderId, products)
        );
        Assertions.assertEquals(exception.getMessage(), "Количество продукта указано неверно. Минимальное значение = 1");
        Mockito.verify(cartRepository, Mockito.never()).insert(Mockito.any(), Mockito.anySet());
    }

    @Test
    void addProducts_error_productNotFound() {
        Long orderId = 1L;
        Set<Product> products = getValidProducts();
        Product product = products.iterator().next();
        Mockito.when(productClient.isProductExists(product.productId())).thenReturn(false);
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> cartService.addProducts(orderId, products)
        );
        Assertions.assertEquals(exception.getMessage(), String.format("Продукт с id %d не найден!", product.productId()));
        Mockito.verify(cartRepository, Mockito.never()).insert(Mockito.any(), Mockito.anySet());
    }

    private Set<Product> getValidProducts() {
        return Set.of(
                Product.builder()
                        .productId(1L)
                        .count(3)
                        .build(),
                Product.builder()
                        .productId(2L)
                        .count(1)
                        .build()
        );
    }
}