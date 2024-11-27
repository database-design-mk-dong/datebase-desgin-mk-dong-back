package com.example.demo.controller;

import com.example.demo.annotation.UserId;
import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.request.device.UpdateDeviceRequestDto;
import com.example.demo.service.device.CommandDeviceService;
import com.example.demo.service.device.QueryDeviceService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hardware")
public class DeviceController {
    private final CommandDeviceService commandDeviceService;
    private final QueryDeviceService queryDeviceService;

    @GetMapping("/control")
    public ResponseDto<?> getDevice(
            @UserId UUID userId,
            @RequestParam(name = "farmID") Long farmId
    ) {
        return ResponseDto.ok(queryDeviceService.getDevice(userId, farmId));
    }

    @PatchMapping("/control")
    public ResponseDto<?> updateDevice(
            @UserId UUID userId,
            @RequestParam(name = "farmID") Long farmId,
            @RequestBody UpdateDeviceRequestDto updateDeviceRequestDto
    ) {
        return ResponseDto.ok(commandDeviceService.updateDevice(updateDeviceRequestDto, userId, farmId));
    }
}
