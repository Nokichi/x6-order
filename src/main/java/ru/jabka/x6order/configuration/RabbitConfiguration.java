package ru.jabka.x6order.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitConfiguration {

    private final RabbitConfigurationProperties configurationProperties;

    @Bean
    public Queue notificationLogQueue() {
        return new Queue(configurationProperties.getQueue(), true);
    }

    @Bean
    public DirectExchange notificationExchange() {
        return new DirectExchange(configurationProperties.getExchange());
    }

    @Bean
    public Binding binding(final Queue queue, final DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .withQueueName();
    }
}