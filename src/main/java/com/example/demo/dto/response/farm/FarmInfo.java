package com.example.demo.dto.response.farm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record FarmInfo(
        Integer farmId,
        String farmName,
        String cropName,

        @JsonProperty("farm_environment")
        String farmEnvironment
) {
}
