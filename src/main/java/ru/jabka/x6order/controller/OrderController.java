package ru.jabka.x6order.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.x6order.model.OrderRequest;
import ru.jabka.x6order.model.OrderResponse;
import ru.jabka.x6order.service.OrderService;

@RestController
@Tag(name = "Заказы")
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse createOrder(@RequestBody final OrderRequest request) {
        return orderService.createOrder(request);
    }
}