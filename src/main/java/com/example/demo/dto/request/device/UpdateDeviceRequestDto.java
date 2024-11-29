package com.example.demo.dto.request.device;

public record UpdateDeviceRequestDto(
        Long deviceId,
        String device,
        Double targetValue
) {
}
