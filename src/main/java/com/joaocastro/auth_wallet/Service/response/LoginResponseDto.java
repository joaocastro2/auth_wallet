package com.joaocastro.auth_wallet.Service.response;

public record LoginResponseDto(

        String token,
        String type,
        Long expiresIn

) {
    public LoginResponseDto(String token, Long expiresIn){
        this(token, "Bearer", expiresIn);
    }
}
