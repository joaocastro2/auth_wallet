package com.joaocastro.auth_wallet.repository;

import com.joaocastro.auth_wallet.model.UsersModel;
import lombok.Builder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<UsersModel, UUID> {

    boolean existsByUserCpf(String userCpf);

}
