package com.example.demo.service.environment;

import com.example.demo.domain.Environment;
import com.example.demo.domain.Farm;
import com.example.demo.domain.User;
import com.example.demo.dto.request.environment.CreateEnvironmentRequestDto;
import com.example.demo.exception.CommonException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.EnvironmentRepository;
import com.example.demo.repository.FarmRepository;
import com.example.demo.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommandEnvironmentService {
    private final UserRepository userRepository;
    private final FarmRepository farmRepository;
    private final EnvironmentRepository environmentRepository;

    public Boolean createEnvironment(CreateEnvironmentRequestDto createEnvironmentRequestDto, UUID userId, Long farmId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        Farm farm = farmRepository.findByUserAndId(user, farmId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_FARM));
        Environment environment = Environment.toEntity(createEnvironmentRequestDto, farm);
        environmentRepository.save(environment);
        return true;
    }
}
