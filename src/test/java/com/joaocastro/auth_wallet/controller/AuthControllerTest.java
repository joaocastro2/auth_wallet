package com.joaocastro.auth_wallet.controller;

import com.joaocastro.auth_wallet.Service.TokenService;
import com.joaocastro.auth_wallet.Service.request.LoginRequestDto;
import com.joaocastro.auth_wallet.model.UsersModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthController authController;

    @Test
    void deveRetornarTokenBearer_QuandoCredenciaisForemValidas() {
        // ARRANGE
        var request = new LoginRequestDto("user@example.com", "secret");
        var user = UsersModel.builder().userId(UUID.randomUUID()).build();
        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willReturn(authentication);
        given(authentication.getPrincipal()).willReturn(user);
        given(tokenService.generateToken(user)).willReturn("jwt-token");
        given(tokenService.getExpirationTime()).willReturn(3_600_000L);

        // ACT
        var response = authController.login(request);

        // ASSERT
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().token()).isEqualTo("jwt-token");
        assertThat(response.getBody().type()).isEqualTo("Bearer");
        assertThat(response.getBody().expiresIn()).isEqualTo(3_600_000L);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService).generateToken(user);
    }
}
