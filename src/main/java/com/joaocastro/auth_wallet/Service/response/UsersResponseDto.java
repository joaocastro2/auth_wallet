package com.joaocastro.auth_wallet.Service.response;

import com.joaocastro.auth_wallet.model.UsersModel;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsersResponseDto(

        UUID userId,
        String userCpf,
        String userName,
        String userMail,
        LocalDateTime createdAt
) {

    public static UsersResponseDto fromEntity(UsersModel users) {
        return new UsersResponseDto(
                users.getUserId(),
                users.getUserCpf(),
                users.getUserName(),
                users.getUserMail(),
                users.getCreatedAt()
        );
    }

}
