package com.example.demo.controller;

import com.example.demo.annotation.UserId;
import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.request.environment.CreateEnvironmentRequestDto;
import com.example.demo.service.environment.CommandEnvironmentService;
import com.example.demo.service.environment.QueryEnvironmentService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/environment")
public class EnvironmentController {
    private final QueryEnvironmentService queryEnvironmentService;
    private final CommandEnvironmentService commandEnvironmentService;

    @GetMapping("/current_environment")
    public ResponseDto<?> getEnvironment(
            @RequestParam(name = "farmID") Long farmId,
            @UserId UUID userId
    ) {
        return ResponseDto.ok(queryEnvironmentService.getEnvironment(farmId, userId));
    }

    @PostMapping("")
    public ResponseDto<?> addEnvironment(
            @RequestBody CreateEnvironmentRequestDto createEnvironmentRequestDto,
            @RequestParam(name = "farmID") Long farmId,
            @UserId UUID userId
    ) {
        return ResponseDto.ok(commandEnvironmentService.createEnvironment(createEnvironmentRequestDto, userId, farmId));
    }
}
