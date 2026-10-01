package com.joaocastro.auth_wallet.controller;

import com.joaocastro.auth_wallet.Service.UsersService;
import com.joaocastro.auth_wallet.Service.request.UsersRequestDto;
import com.joaocastro.auth_wallet.Service.response.UsersResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    @RequestMapping("/register")
    public ResponseEntity<UsersResponseDto> registerUser(@RequestBody @Valid UsersRequestDto usersRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(usersService.registerUser(usersRequest));
    }

    @GetMapping
    public ResponseEntity<List<UsersResponseDto>> findAllUsers() {
        List<UsersResponseDto> users = usersService.findAll();
        return ResponseEntity.ok(users);
    }
}
