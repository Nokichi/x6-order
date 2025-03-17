package ru.jabka.x6order.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.x6order.client.UserClient;
import ru.jabka.x6order.exception.BadRequestException;
import ru.jabka.x6order.model.Order;
import ru.jabka.x6order.model.OrderRequest;
import ru.jabka.x6order.model.OrderResponse;
import ru.jabka.x6order.model.Product;
import ru.jabka.x6order.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserClient userClient;

    @Mock
    private CartService cartService;

    @InjectMocks
    private OrderService orderService;

    @Test
    void create_success() {
        final Order order = getValidOrder();
        final OrderResponse response = new OrderResponse(order.id());
        final OrderRequest request = getValidOrderRequest();
        Mockito.when(userClient.isUserExists(request.userId())).thenReturn(true);
        Mockito.when(orderRepository.insert(request.userId())).thenReturn(order);
        OrderResponse result = orderService.createOrder(request);
        Assertions.assertEquals(response, result);
        Mockito.verify(orderRepository).insert(request.userId());
        Mockito.verify(userClient).isUserExists(request.userId());
        Mockito.verify(cartService).addProducts(order.id(), request.products());
    }

    @Test
    void create_error_userIdNotFound() {
        final OrderRequest request = getValidOrderRequest();
        Mockito.when(userClient.isUserExists(request.userId())).thenReturn(false);
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> orderService.createOrder(request)
        );
        Assertions.assertEquals(exception.getMessage(), String.format("Пользователь с id %d не найден!", request.userId()));
        Mockito.verify(orderRepository, Mockito.never()).insert(request.userId());
    }

    @Test
    void create_error_userIdIsNull() {
        Set<Product> products = Set.of(
                Product.builder()
                        .productId(1L)
                        .count(3)
                        .build(),
                Product.builder()
                        .productId(2L)
                        .count(1)
                        .build()
        );
        final OrderRequest request = OrderRequest.builder()
                .userId(null)
                .products(products)
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> orderService.createOrder(request)
        );
        Assertions.assertEquals(exception.getMessage(), "Заполните id пользователя, создающего заказ");
        Mockito.verify(orderRepository, Mockito.never()).insert(request.userId());
    }

    private OrderRequest getValidOrderRequest() {
        Set<Product> products = Set.of(
                Product.builder()
                        .productId(1L)
                        .count(3)
                        .build(),
                Product.builder()
                        .productId(2L)
                        .count(1)
                        .build()
        );
        return OrderRequest.builder()
                .userId(1L)
                .products(products)
                .build();
    }

    private Order getValidOrder() {
        return Order.builder()
                .id(1L)
                .userId(2L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}