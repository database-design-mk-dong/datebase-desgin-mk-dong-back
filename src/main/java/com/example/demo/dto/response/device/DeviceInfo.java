package com.example.demo.dto.response.device;

import lombok.Builder;

@Builder
public record DeviceInfo(
        Long deviceId,
        String device,
        Double status
) {
}
