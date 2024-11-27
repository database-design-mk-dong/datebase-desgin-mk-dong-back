package com.example.demo.controller;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.request.user.CreateUserRequestDto;
import com.example.demo.dto.request.user.LoginUserRequestDto;
import com.example.demo.service.user.CommandUserService;
import com.example.demo.service.user.QueryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final CommandUserService commandUserService;
    private final QueryUserService queryUserService;

    @PostMapping("/register")
    public ResponseDto<?> registerUser(
            @RequestBody CreateUserRequestDto createUserRequestDto
    ) {
        return ResponseDto.ok(commandUserService.userRegister(createUserRequestDto));
    }

    @PostMapping("/login")
    public ResponseDto<?> loginUser(
            @RequestBody LoginUserRequestDto loginUserRequestDto
    ) {
        return ResponseDto.ok(queryUserService.userLogin(loginUserRequestDto));
    }
}
