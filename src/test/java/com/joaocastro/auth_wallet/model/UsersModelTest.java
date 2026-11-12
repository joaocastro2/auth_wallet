package com.joaocastro.auth_wallet.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UsersModelTest {

    @Test
    void deveDefinirDataDeCriacao_QuandoAindaNaoExistirData() {
        // ARRANGE
        var user = UsersModel.builder().build();

        // ACT
        user.prePersist();

        // ASSERT
        assertThat(user.getCreatedAt()).isNotNull();
    }

    @Test
    void naoDeveAlterarDataDeCriacao_QuandoDataJaExistir() {
        // ARRANGE
        var createdAt = LocalDateTime.now().minusDays(1);
        var user = UsersModel.builder().createdAt(createdAt).build();

        // ACT
        user.prePersist();

        // ASSERT
        assertThat(user.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    void deveExporEmailComoUsernameESenhaComoPassword() {
        // ARRANGE
        var user = UsersModel.builder()
                .userMail("user@example.com")
                .userPassword("hashed")
                .build();

        // ACT & ASSERT
        assertThat(user.getUsername()).isEqualTo("user@example.com");
        assertThat(user.getPassword()).isEqualTo("hashed");
        assertThat(user.getAuthorities()).isEmpty();
    }
}
