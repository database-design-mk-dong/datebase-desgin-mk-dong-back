package com.example.demo.dto.request.user;

import jakarta.validation.constraints.NotNull;

public record LoginUserRequestDto(
        @NotNull(message = "이메일 입력")
        String email,
        @NotNull(message = "패스워드 입력")
        String password
) {
}
