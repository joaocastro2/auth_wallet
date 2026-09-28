package com.joaocastro.auth_wallet.controller;

import com.joaocastro.auth_wallet.Service.UsersService;
import com.joaocastro.auth_wallet.Service.request.UsersRequestDto;
import com.joaocastro.auth_wallet.Service.response.UsersResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    @RequestMapping("/register")
    public ResponseEntity<UsersResponseDto> registerUser(@RequestBody @Validated UsersRequestDto usersRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(usersService.registerUser(usersRequest));
    }
}
