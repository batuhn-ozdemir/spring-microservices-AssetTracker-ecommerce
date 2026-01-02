package com.batuhn_ozdemir.notification_service;

import com.batuhn_ozdemir.notification_service.event.OrderPlacedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationService {

    @RabbitListener(queues = "notificationQueue")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
        log.info("Notification received from RabbitMQ - Order No: {}", orderPlacedEvent.getOrderNumber());
    }
}
