package com.joaocastro.auth_wallet.Service;

import com.joaocastro.auth_wallet.Service.request.UsersRequestDto;
import com.joaocastro.auth_wallet.kafka.producer.UserProducer;
import com.joaocastro.auth_wallet.model.UsersModel;
import com.joaocastro.auth_wallet.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    private static final String CPF = "12345678901";
    private static final String NAME = "Maria Silva";
    private static final String EMAIL = "maria@example.com";
    private static final String RAW_PASSWORD = "secret";
    private static final String HASHED_PASSWORD = "hashed-secret";

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserProducer userProducer;

    @InjectMocks
    private UsersService usersService;

    @Test
    void deveRegistrarUsuarioEPublicarEvento_QuandoDadosForemValidos() {
        // ARRANGE
        var request = new UsersRequestDto(CPF, NAME, EMAIL, RAW_PASSWORD);
        var id = UUID.randomUUID();
        var createdAt = LocalDateTime.now();
        var savedUser = UsersModel.builder()
                .userId(id)
                .userCpf(CPF)
                .userName(NAME)
                .userMail(EMAIL)
                .userPassword(HASHED_PASSWORD)
                .createdAt(createdAt)
                .build();
        given(usersRepository.existsByUserCpf(CPF)).willReturn(false);
        given(passwordEncoder.encode(RAW_PASSWORD)).willReturn(HASHED_PASSWORD);
        given(usersRepository.save(any(UsersModel.class))).willReturn(savedUser);

        // ACT
        var result = usersService.registerUser(request);

        // ASSERT
        assertThat(result.userId()).isEqualTo(id);
        assertThat(result.userCpf()).isEqualTo(CPF);
        assertThat(result.userName()).isEqualTo(NAME);
        assertThat(result.userMail()).isEqualTo(EMAIL);
        assertThat(result.createdAt()).isEqualTo(createdAt);

        var savedCaptor = ArgumentCaptor.forClass(UsersModel.class);
        verify(usersRepository).save(savedCaptor.capture());
        assertThat(savedCaptor.getValue().getUserPassword()).isEqualTo(HASHED_PASSWORD);
        verify(userProducer).publishUserCreated(any());
    }

    @Test
    void deveLancarExcecaoENaoSalvar_QuandoCpfJaEstiverCadastrado() {
        // ARRANGE
        var request = new UsersRequestDto(CPF, NAME, EMAIL, RAW_PASSWORD);
        given(usersRepository.existsByUserCpf(CPF)).willReturn(true);

        // ACT & ASSERT
        assertThatThrownBy(() -> usersService.registerUser(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cpf já cadastrado");
        verify(usersRepository, never()).save(any());
        verifyNoInteractions(passwordEncoder, userProducer);
    }

    @Test
    void deveRetornarUsuariosMapeados_QuandoExistiremUsuarios() {
        // ARRANGE
        var user = UsersModel.builder()
                .userId(UUID.randomUUID())
                .userCpf(CPF)
                .userName(NAME)
                .userMail(EMAIL)
                .createdAt(LocalDateTime.now())
                .build();
        given(usersRepository.findAll()).willReturn(List.of(user));

        // ACT
        var result = usersService.findAll();

        // ASSERT
        assertThat(result).singleElement()
                .extracting("userId", "userCpf", "userName", "userMail")
                .containsExactly(user.getUserId(), CPF, NAME, EMAIL);
        verify(usersRepository).findAll();
    }
}
