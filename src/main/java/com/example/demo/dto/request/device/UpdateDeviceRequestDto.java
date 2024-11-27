package com.example.demo.dto.request.device;

public record UpdateDeviceRequestDto(
        String device,
        Double targetValue
) {
}
