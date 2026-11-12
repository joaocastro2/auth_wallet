package com.joaocastro.auth_wallet.kafka.producer;

import com.joaocastro.auth_wallet.kafka.event.UserCreatedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserProducerTest {

    private static final String TOPIC = "user-created-topic";

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private UserProducer userProducer;

    @Test
    void devePublicarEventoNoTopicoComIdComoChave() {
        // ARRANGE
        var id = UUID.randomUUID();
        var event = new UserCreatedEvent(id, "Maria", "maria@example.com", LocalDateTime.now());

        // ACT
        userProducer.publishUserCreated(event);

        // ASSERT
        verify(kafkaTemplate).send(TOPIC, id.toString(), event);
    }
}
