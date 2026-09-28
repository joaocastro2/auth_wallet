package com.joaocastro.auth_wallet.Service.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsersResponseDto(

        UUID userId,
        String userCpf,
        String userName,
        String userMail,
        LocalDateTime createdAt
) {
}
