package com.example.demo.dto.request.user;

import jakarta.validation.constraints.NotNull;

public record CreateUserRequestDto(
        @NotNull(message = "이메일 입력")
        String email,

        @NotNull(message = "비밀번호 입력")
        String password,

        @NotNull(message = "닉네임 입력")
        String username

) {
}
