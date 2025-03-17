package ru.jabka.x6order.client;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import ru.jabka.x6order.configuration.RabbitConfigurationProperties;

@Component
@RequiredArgsConstructor
public class QueueClient {

    private final RabbitTemplate rabbit;
    private final RabbitConfigurationProperties properties;

    public void sendNotification(final Long orderId) {
        rabbit.convertAndSend(properties.getExchange(), properties.getQueue(), orderId);
    }
}