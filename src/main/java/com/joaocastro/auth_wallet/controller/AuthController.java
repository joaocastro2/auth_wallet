package com.joaocastro.auth_wallet.controller;

import com.joaocastro.auth_wallet.Service.TokenService;
import com.joaocastro.auth_wallet.Service.request.LoginRequestDto;
import com.joaocastro.auth_wallet.Service.response.LoginResponseDto;
import com.joaocastro.auth_wallet.model.UsersModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto dto){
        var authToken = new UsernamePasswordAuthenticationToken(dto.userMail(), dto.userPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);
        UsersModel user = (UsersModel) authentication.getPrincipal();
        String token = tokenService.generateToken(user);

        return ResponseEntity.ok(new LoginResponseDto(token, tokenService.getExpirationTime()));
    }

}
