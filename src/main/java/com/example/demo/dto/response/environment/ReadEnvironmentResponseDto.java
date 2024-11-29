package com.example.demo.dto.response.environment;

import com.example.demo.domain.Environment;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;

@Builder
public record ReadEnvironmentResponseDto(
        String timestamp,
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
                .timestamp(environment.getTimestamp().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
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

