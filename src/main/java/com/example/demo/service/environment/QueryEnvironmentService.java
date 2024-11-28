package com.example.demo.service.environment;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryEnvironmentService {

    public Boolean getEnvironment(Long farmId, UUID userId) {
        return null;
    }
}
