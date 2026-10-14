package com.joaocastro.auth_wallet.kafka.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserCreatedEvent(

        UUID userId,
        String userName,
        String userMail,
        LocalDateTime createdAt

) {
}
