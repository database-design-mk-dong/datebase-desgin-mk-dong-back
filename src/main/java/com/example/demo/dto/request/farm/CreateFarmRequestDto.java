package com.example.demo.dto.request.farm;

import com.example.demo.dto.request.device.DeviceListRequestDto;
import java.util.List;

public record CreateFarmRequestDto(
        String farmName,
        String cropName,
        List<DeviceListRequestDto> devices
) {
}
