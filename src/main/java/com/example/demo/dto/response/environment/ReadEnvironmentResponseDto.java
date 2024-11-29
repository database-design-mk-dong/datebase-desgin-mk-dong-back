package com.example.demo.dto.response.environment;

import com.example.demo.domain.Environment;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ReadEnvironmentResponseDto(
        LocalDateTime timestamp,
        Double n,
        Double p,
        Double k,
        Double temperature,
        Double humidity,
        Double ph,
        Double rainfall
) {
    public static ReadEnvironmentResponseDto of(Environment environment) {
        return ReadEnvironmentResponseDto.builder()
                .timestamp(environment.getTimestamp())
                .n(environment.getN())
                .p(environment.getP())
                .k(environment.getK())
                .temperature(environment.getTemperature())
                .humidity(environment.getHumidity())
                .ph(environment.getPh())
                .rainfall(environment.getRainfall())
                .build();
    }
}

