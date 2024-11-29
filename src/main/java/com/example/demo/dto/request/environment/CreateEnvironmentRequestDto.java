package com.example.demo.dto.request.environment;

import java.time.LocalDateTime;

public record CreateEnvironmentRequestDto(
        Double n,
        Double p,
        Double k,
        Double temperature,
        Double humidity,
        Double ph,
        Double rainfall
) {
}
