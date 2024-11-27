package com.example.demo.service.farm;

import com.example.demo.domain.Environment;
import com.example.demo.domain.Farm;
import com.example.demo.domain.FarmCrop;
import com.example.demo.domain.User;
import com.example.demo.dto.response.farm.FarmEnvironment;
import com.example.demo.dto.response.farm.ReadFarmResponseDto;
import com.example.demo.exception.CommonException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.CropRepository;
import com.example.demo.repository.DeviceRepository;
import com.example.demo.repository.EnvironmentRepository;
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
@Transactional(readOnly = true)
public class QueryFarmService {
    private final FarmRepository farmRepository;
    private final UserRepository userRepository;
    private final EnvironmentRepository environmentRepository;
    private final CropRepository cropRepository;
    private final FarmCropRepository farmCropRepository;
    private final DeviceRepository deviceRepository;
    private final FarmDeviceRepository farmDeviceRepository;

    public List<ReadFarmResponseDto> readFarm(UUID uuid) {
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        List<Farm> farms = farmRepository.findAllByUser(user);

        return farms.stream()
                .map(this::getFarmInfo)
                .toList();
    }

    public ReadFarmResponseDto readFarmDetail(Long farmId, UUID uuid) {
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Farm farm = farmRepository.findByUserAndId(user, farmId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        return getFarmInfo(farm);
    }

    private ReadFarmResponseDto getFarmInfo(Farm farm) {
        FarmCrop farmCrop = farm.getFarmCrops().stream().findFirst()
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_CROP));

        Environment environment = environmentRepository.findByFarm(farm)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ENVIRONMENT));
        FarmEnvironment farmEnvironment = FarmEnvironment.of(environment);

        return ReadFarmResponseDto.builder()
                .farmId(farm.getId().intValue())
                .farmName(farm.getFarmName())
                .cropName(farmCrop.getCrop().getCropName())
                .farmEnvironment(farmEnvironment)
                .build();
    }
}
