package com.joaocastro.auth_wallet.Service.request;

public record UsersRequestDto(

        String userName,
        String userCpf,
        String userMail,
        String userPassword

){}
