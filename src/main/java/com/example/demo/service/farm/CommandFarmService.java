package com.example.demo.service.farm;

import com.example.demo.domain.Crop;
import com.example.demo.domain.Device;
import com.example.demo.domain.Farm;
import com.example.demo.domain.FarmCrop;
import com.example.demo.domain.FarmDevice;
import com.example.demo.domain.User;
import com.example.demo.dto.request.farm.CreateFarmRequestDto;
import com.example.demo.dto.request.farm.UpdateFarmRequestDto;
import com.example.demo.dto.response.farm.CreateFarmResponseDto;
import com.example.demo.dto.response.device.DeviceInfo;
import com.example.demo.dto.response.farm.FarmInfo;
import com.example.demo.exception.CommonException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.CropRepository;
import com.example.demo.repository.DeviceRepository;
import com.example.demo.repository.FarmCropRepository;
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
@Transactional
public class CommandFarmService {
    private final FarmRepository farmRepository;
    private final UserRepository userRepository;
    private final CropRepository cropRepository;
    private final FarmCropRepository farmCropRepository;
    private final DeviceRepository deviceRepository;
    private final FarmDeviceRepository farmDeviceRepository;

    public CreateFarmResponseDto createFarm(CreateFarmRequestDto createFarmRequestDto, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Farm farm = Farm.toEntity(createFarmRequestDto.farmName(), user);
        farmRepository.save(farm);

        Crop crop = Crop.toEntity(createFarmRequestDto.cropName());
        cropRepository.save(crop);

        FarmCrop farmCrop = FarmCrop.toEntity(farm, crop);
        farmCropRepository.save(farmCrop);

        List<Device> devices = createFarmRequestDto.devices().stream()
                .map(deviceListRequestDto -> Device.toEntity(deviceListRequestDto.device()))
                .toList();
        deviceRepository.saveAll(devices);

        List<FarmDevice> farmDevices = devices.stream()
                .map(device -> FarmDevice.toEntity(farm, device))
                .toList();
        farmDeviceRepository.saveAll(farmDevices);

        FarmInfo farmInfo = getFarmInfo(farm,crop);
        List<DeviceInfo> deviceInfos = farmDevices.stream()
                .map(this::getDeviceInfo)
                .toList();
        return CreateFarmResponseDto.of(farmInfo,deviceInfos);
    }

    public FarmInfo updateFarm(UpdateFarmRequestDto updateFarmRequestDto, UUID userId, Long farmId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Farm farm = farmRepository.findByUserAndId(user, farmId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        farm.updateFarm(updateFarmRequestDto.newFarmName());
        farmRepository.save(farm);

        FarmCrop farmCrop = farm.getFarmCrops().stream().findFirst()
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_CROP));

        return FarmInfo.builder()
                .farmId(farmId.intValue())
                .farmName(farm.getFarmName())
                .cropName(farmCrop.getCrop().getCropName())
                .build();
    }

    public Boolean deleteFarm(UUID userId, Long farmId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Farm farm = farmRepository.findByUserAndId(user, farmId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        farmRepository.delete(farm);
        return true;
    }

    private FarmInfo getFarmInfo(Farm farm, Crop crop) {
        return FarmInfo.builder()
                .farmId(farm.getId().intValue())
                .farmName(farm.getFarmName())
                .cropName(crop.getCropName())
                .farmEnvironment(null)
                .build();
    }

    private DeviceInfo getDeviceInfo(FarmDevice farmDevice) {
        return DeviceInfo.builder()
                .device(farmDevice.getDevice().getDeviceName())
                .status(farmDevice.getStatus())
                .build();
    }
}
