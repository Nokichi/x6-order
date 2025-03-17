package ru.jabka.x6order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jabka.x6order.client.QueueClient;
import ru.jabka.x6order.client.UserClient;
import ru.jabka.x6order.exception.BadRequestException;
import ru.jabka.x6order.model.Order;
import ru.jabka.x6order.model.OrderRequest;
import ru.jabka.x6order.model.OrderResponse;
import ru.jabka.x6order.repository.OrderRepository;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final CartService cartService;
    private final QueueClient queueClient;

    @Transactional(rollbackFor = Throwable.class)
    public OrderResponse createOrder(OrderRequest request) {
        Order order = createOrder(request.userId());
        cartService.addProducts(order.id(), request.products());
        queueClient.sendNotification(order.id());
        return new OrderResponse(order.id());
    }

    private Order createOrder(final Long userId) {
        validateUser(userId);
        return orderRepository.insert(userId);
    }

    private void validateUser(final Long userId) {
        ofNullable(userId).orElseThrow(() -> new BadRequestException("Заполните id пользователя, создающего заказ"));
        if (!userClient.isUserExists(userId)) {
            throw new BadRequestException(String.format("Пользователь с id %d не найден!", userId));
        }
    }
}