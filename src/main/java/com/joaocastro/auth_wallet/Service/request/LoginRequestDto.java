package com.joaocastro.auth_wallet.Service.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail invalido")
        String userMail,

        @NotBlank(message = "A senha é obrigatória")
        String userPassword

){}
