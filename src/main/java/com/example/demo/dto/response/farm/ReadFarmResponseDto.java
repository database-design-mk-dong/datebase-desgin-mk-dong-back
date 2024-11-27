package com.example.demo.dto.response.farm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ReadFarmResponseDto(
//        Integer farmId,
//        "farmID": 8,
//                "farmName": "farm1",
//                "cropName": "Rice",
//                "farm_environment": {
//          "timestamp": datetime,
//            "N": double,
//            "P": double,
//            "K": double,
//            "temperature": double,
//            "humidity": double,
//            "ph": double,
//            "rainfall": double
            Integer farmId,
            String farmName,
            String cropName,
            @JsonProperty("farm_environment")
            FarmEnvironment farmEnvironment
) {
}
