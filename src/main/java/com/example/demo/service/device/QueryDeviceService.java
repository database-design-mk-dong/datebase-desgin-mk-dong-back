package com.example.demo.service.device;

import com.example.demo.domain.Device;
import com.example.demo.domain.Farm;
import com.example.demo.domain.FarmDevice;
import com.example.demo.domain.User;
import com.example.demo.dto.response.device.DeviceInfo;
import com.example.demo.exception.CommonException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.DeviceRepository;
import com.example.demo.repository.FarmDeviceRepository;
import com.example.demo.repository.FarmRepository;
import com.example.demo.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryDeviceService {
    private final FarmRepository farmRepository;
    private final UserRepository userRepository;

    public List<DeviceInfo> getDevice(UUID userId, Long farmId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Farm farm = farmRepository.findByUserAndId(user, farmId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        return farm.getFarmDevices().stream()
                .map(farmDevice -> DeviceInfo.builder()
                        .deviceId(farmDevice.getId())
                        .device(farmDevice.getDevice().getDeviceName())
                        .status(farmDevice.getStatus())
                        .build()
                ).toList();
    }

}
