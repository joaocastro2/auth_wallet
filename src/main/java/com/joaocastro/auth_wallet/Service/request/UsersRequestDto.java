package com.joaocastro.auth_wallet.Service.request;

import jakarta.validation.constraints.NotBlank;

public record UsersRequestDto(

        @NotBlank String userCpf,
        @NotBlank String userName,
        @NotBlank String userMail,
        @NotBlank String userPassword

){}
