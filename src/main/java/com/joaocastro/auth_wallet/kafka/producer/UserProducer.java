package com.joaocastro.auth_wallet.kafka.producer;

import com.joaocastro.auth_wallet.kafka.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "user-created-topic";

    public void publishUserCreated(UserCreatedEvent event) {
        log.info("Publicando evento de novo usuário no kafka. UserID: {}", event.userId());
        kafkaTemplate.send(TOPIC, event.userId().toString(), event);
    }
}
