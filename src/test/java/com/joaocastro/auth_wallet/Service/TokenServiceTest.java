package com.joaocastro.auth_wallet.Service;

import com.joaocastro.auth_wallet.model.UsersModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TokenServiceTest {

    private static final String SECRET = "a-secret-with-at-least-32-characters";
    private static final long EXPIRATION = 3_600_000L;

    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        tokenService = new TokenService();
        ReflectionTestUtils.setField(tokenService, "secret", SECRET);
        ReflectionTestUtils.setField(tokenService, "expiration", EXPIRATION);
    }

    @Test
    void deveGerarTokenComClaimsDoUsuarioEDataDeExpiracao() {
        // ARRANGE
        var id = UUID.randomUUID();
        var user = UsersModel.builder()
                .userId(id)
                .userMail("user@example.com")
                .userName("Maria")
                .build();

        // ACT
        var token = tokenService.generateToken(user);

        // ASSERT
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
        assertThat(claims.getSubject()).isEqualTo(id.toString());
        assertThat(claims.get("email", String.class)).isEqualTo("user@example.com");
        assertThat(claims.get("name", String.class)).isEqualTo("Maria");
        assertThat(claims.getExpiration()).isAfter(claims.getIssuedAt());
        assertThat(claims.getExpiration().getTime() - claims.getIssuedAt().getTime())
                .isEqualTo(EXPIRATION);
    }

    @Test
    void deveRetornarTempoDeExpiracaoConfigurado() {
        // ARRANGE & ACT
        var result = tokenService.getExpirationTime();

        // ASSERT
        assertThat(result).isEqualTo(EXPIRATION);
    }
}
