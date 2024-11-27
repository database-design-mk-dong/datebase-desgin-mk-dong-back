package com.example.demo.dto.request.device;

import lombok.Builder;

@Builder
public record DeviceListRequestDto(
        String device
) {
    public static DeviceListRequestDto of(String device) {
        return DeviceListRequestDto.builder()
                .device(device)
                .build();
    }
}
