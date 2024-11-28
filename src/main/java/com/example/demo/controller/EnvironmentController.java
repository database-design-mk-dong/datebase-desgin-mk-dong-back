package com.example.demo.controller;

import com.example.demo.annotation.UserId;
import com.example.demo.dto.common.ResponseDto;
import com.example.demo.service.environment.QueryEnvironmentService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/environment")
public class EnvironmentController {
    private final QueryEnvironmentService queryEnvironmentService;

    public ResponseDto<?> getEnvironment(
            @RequestParam(name = "farmID") Long farmId,
            @UserId UUID userId
    ) {
        return ResponseDto.ok(queryEnvironmentService.getEnvironment(farmId, userId));
    }
}
