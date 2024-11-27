package com.example.demo.dto.response.farm;

import com.example.demo.domain.Environment;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record FarmEnvironment(
//        "farm_environment": {
//          "timestamp": datetime,
//            "N": double,
//            "P": double,
//            "K": double,
//            "temperature": double,
//            "humidity": double,
//            "ph": double,
//            "rainfall": double
            LocalDateTime timestamp,
            Double n,
            Double p,
            Double k,
            Double temperature,
            Double humidity,
            Double ph,
            Double rainfall
) {
    public static FarmEnvironment of(Environment environment) {
        return FarmEnvironment.builder()
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
