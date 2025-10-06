package com.fiap.techchallenge.appointment_history_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "history_exchange";
    public static final String ROUTING = "history";
    public static final String QUEUE = "history_queue";

    public static final String DLX = "history_dlx";
    public static final String DLQ = "history_queue_dlq";
    public static final String DLQ_ROUTING = "history_dlq";

    @Bean
    TopicExchange appointmentsExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    TopicExchange historyDlx() {
        return new TopicExchange(DLX, true, false);
    }

    @Bean
    Queue historyQueue() {
        return QueueBuilder.durable(QUEUE)
                .withArguments(Map.of(
                        "x-dead-letter-exchange", DLX,
                        "x-dead-letter-routing-key", DLQ_ROUTING
                ))
                .build();
    }

    @Bean
    Queue historyDlq() {
        return QueueBuilder.durable(DLQ).build();
    }

    @Bean
    Binding bindCompleted(Queue historyQueue, TopicExchange appointmentsExchange) {
        return BindingBuilder.bind(historyQueue).to(appointmentsExchange).with(ROUTING);
    }

    @Bean
    Binding bindDlq(Queue historyDlq, TopicExchange historyDlx) {
        return BindingBuilder.bind(historyDlq).to(historyDlx).with(DLQ_ROUTING);
    }

}
