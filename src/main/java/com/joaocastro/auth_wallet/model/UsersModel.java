package com.joaocastro.auth_wallet.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_users")
public class UsersModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String userCpf;

    @Column(name = "name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false, length = 50)
    private String userMail;

    @Column(name = "password", nullable = false)
    private String userPassword;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
