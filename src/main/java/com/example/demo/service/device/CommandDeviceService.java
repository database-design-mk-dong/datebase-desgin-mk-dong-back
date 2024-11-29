package com.example.demo.service.device;

import com.example.demo.domain.Device;
import com.example.demo.domain.Farm;
import com.example.demo.domain.FarmDevice;
import com.example.demo.domain.User;
import com.example.demo.dto.request.device.UpdateDeviceRequestDto;
import com.example.demo.dto.response.device.DeviceInfo;
import com.example.demo.exception.CommonException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.CropRepository;
import com.example.demo.repository.DeviceRepository;
import com.example.demo.repository.FarmCropRepository;
import com.example.demo.repository.FarmDeviceRepository;
import com.example.demo.repository.FarmRepository;
import com.example.demo.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommandDeviceService {
    private final FarmRepository farmRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final FarmDeviceRepository farmDeviceRepository;

    public DeviceInfo updateDevice(UpdateDeviceRequestDto updateDeviceRequestDto, UUID userId, Long farmId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Farm farm = farmRepository.findByUserAndId(user, farmId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Device device = deviceRepository.findById(updateDeviceRequestDto.deviceId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DEVICE));

        FarmDevice farmDevice = farmDeviceRepository.findByFarmAndDevice(farm, device)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_FARMDEVICE));

        farmDevice.updateStatus(updateDeviceRequestDto.targetValue());
        farmDeviceRepository.save(farmDevice);

        return DeviceInfo.builder()
                .device(device.getDeviceName())
                .status(farmDevice.getStatus())
                .build();

    }
}
