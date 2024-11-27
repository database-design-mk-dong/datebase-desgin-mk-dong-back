package com.example.demo.dto.response.device;

import lombok.Builder;

@Builder
public record DeviceInfo(
        String device,
        Double status
) {
}
