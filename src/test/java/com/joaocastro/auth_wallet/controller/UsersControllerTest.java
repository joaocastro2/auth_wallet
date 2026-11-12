package com.joaocastro.auth_wallet.controller;

import com.joaocastro.auth_wallet.Service.UsersService;
import com.joaocastro.auth_wallet.Service.request.UsersRequestDto;
import com.joaocastro.auth_wallet.Service.response.UsersResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @Mock
    private UsersService usersService;

    @InjectMocks
    private UsersController usersController;

    @Test
    void deveRetornarCreated_QuandoRegistrarUsuario() {
        // ARRANGE
        var request = new UsersRequestDto("12345678901", "Maria", "maria@example.com", "secret");
        var response = new UsersResponseDto(
                UUID.randomUUID(), request.userCpf(), request.userName(), request.userMail(), LocalDateTime.now());
        given(usersService.registerUser(request)).willReturn(response);

        // ACT
        var result = usersController.registerUser(request);

        // ASSERT
        assertThat(result.getStatusCode().value()).isEqualTo(201);
        assertThat(result.getBody()).isEqualTo(response);
        verify(usersService).registerUser(request);
    }

    @Test
    void deveRetornarListaDeUsuarios_QuandoConsultarUsuarios() {
        // ARRANGE
        var users = List.of(new UsersResponseDto(
                UUID.randomUUID(), "12345678901", "Maria", "maria@example.com", LocalDateTime.now()));
        given(usersService.findAll()).willReturn(users);

        // ACT
        var result = usersController.findAllUsers();

        // ASSERT
        assertThat(result.getStatusCode().value()).isEqualTo(200);
        assertThat(result.getBody()).containsExactlyElementsOf(users);
        verify(usersService).findAll();
    }
}
