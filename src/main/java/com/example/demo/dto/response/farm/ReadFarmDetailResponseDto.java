package com.example.demo.dto.response.farm;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

@Builder
public record ReadFarmDetailResponseDto(

        Integer farmId,
        String farmName,
        String cropName,
        @JsonProperty("farm_environment")
        List<FarmEnvironment> farmEnvironment
) {
}
