package com.fiap.techchallenge.appointment_history_service.adapters.in.rabbit;

import com.fiap.techchallenge.appointment_history_service.application.usecase.SaveAppointmentHistoryFromEventCase;
import com.fiap.techchallenge.appointment_history_service.exception.InvalidEventPayloadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

import static com.fiap.techchallenge.appointment_history_service.config.RabbitConfig.QUEUE;


@Slf4j
@Component
@RequiredArgsConstructor
public class AppointmentEventListener {

    private final SaveAppointmentHistoryFromEventCase saveHistory;

    @RabbitListener(queues = QUEUE)
    public void onMessage(Message msg) {
        try {
            String routingKey = msg.getMessageProperties().getReceivedRoutingKey();
            String body = new String(msg.getBody(), StandardCharsets.UTF_8);

            log.info("message in queue={} rk={}",
                    msg.getMessageProperties().getConsumerQueue(), routingKey);

            saveHistory.handle(routingKey, body);
        } catch (InvalidEventPayloadException e) {
            throw new AmqpRejectAndDontRequeueException(e.getMessage(), e);
        }
    }
}
