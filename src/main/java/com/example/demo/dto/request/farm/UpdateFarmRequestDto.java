package com.example.demo.dto.request.farm;

import jakarta.validation.constraints.NotNull;

public record UpdateFarmRequestDto(
        @NotNull(message = "새로운 농장 이름 입력")
        String newFarmName
) {
}
