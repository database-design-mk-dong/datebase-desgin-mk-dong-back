package com.example.demo.dto.response.farm;

import com.example.demo.dto.response.device.DeviceInfo;
import java.util.List;
import lombok.Builder;

@Builder
public record CreateFarmResponseDto(
        FarmInfo farmInfo,
        List<DeviceInfo> device
) {
    public static CreateFarmResponseDto of(FarmInfo farmInfo, List<DeviceInfo> device) {
        return CreateFarmResponseDto.builder()
                .farmInfo(farmInfo)
                .device(device)
                .build();
    }
}
