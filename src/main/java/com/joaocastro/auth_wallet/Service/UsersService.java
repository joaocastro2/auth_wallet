package com.joaocastro.auth_wallet.Service;

import com.joaocastro.auth_wallet.Service.request.UsersRequestDto;
import com.joaocastro.auth_wallet.Service.response.UsersResponseDto;
import com.joaocastro.auth_wallet.model.UsersModel;
import com.joaocastro.auth_wallet.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsersResponseDto registerUser(UsersRequestDto usersRequest){
        if (usersRepository.existsByUserCpf(usersRequest.userCpf())){
            throw new IllegalArgumentException("Cpf já cadastrado");
        }

        UsersModel usersModel = UsersModel.builder()
                .userCpf(usersRequest.userCpf())
                .userName(usersRequest.userName())
                .userMail(usersRequest.userMail())
                .userPassword(passwordEncoder.encode(usersRequest.userPassword()))
                .build();

        UsersModel savedUser = usersRepository.save(usersModel);
        return new UsersResponseDto(savedUser.getUserId(), savedUser.getUserCpf(), savedUser.getUserName(), savedUser.getUserMail(), savedUser.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public List<UsersResponseDto> findAll() {
        return usersRepository.findAll()
                .stream()
                .map(UsersResponseDto::fromEntity)
                .toList();
    }
}
