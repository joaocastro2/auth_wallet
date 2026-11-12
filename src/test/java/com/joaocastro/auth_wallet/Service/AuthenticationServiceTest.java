package com.joaocastro.auth_wallet.Service;

import com.joaocastro.auth_wallet.model.UsersModel;
import com.joaocastro.auth_wallet.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private static final String EMAIL = "user@example.com";

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void deveRetornarUsuario_QuandoEmailEstiverCadastrado() {
        // ARRANGE
        var user = UsersModel.builder().userMail(EMAIL).build();
        given(usersRepository.findByUserMail(EMAIL)).willReturn(Optional.of(user));

        // ACT
        var result = authenticationService.loadUserByUsername(EMAIL);

        // ASSERT
        assertThat(result).isSameAs(user);
        verify(usersRepository).findByUserMail(EMAIL);
        verifyNoMoreInteractions(usersRepository);
    }

    @Test
    void deveLancarExcecao_QuandoEmailNaoEstiverCadastrado() {
        // ARRANGE
        given(usersRepository.findByUserMail(EMAIL)).willReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> authenticationService.loadUserByUsername(EMAIL))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining(EMAIL);
        verify(usersRepository).findByUserMail(EMAIL);
    }
}
