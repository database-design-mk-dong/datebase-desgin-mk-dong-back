package com.example.demo.controller;

import com.example.demo.annotation.UserId;
import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.request.farm.CreateFarmRequestDto;
import com.example.demo.dto.request.farm.UpdateFarmRequestDto;
import com.example.demo.service.farm.CommandFarmService;
import com.example.demo.service.farm.QueryFarmService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/farm")
public class FarmController {
    private final CommandFarmService commandFarmService;
    private final QueryFarmService queryFarmService;

    @PostMapping("/createFarm")
    public ResponseDto<?> createFarm(
                @RequestBody CreateFarmRequestDto createFarmRequestDto,
                @UserId UUID userId
    ) {
        return ResponseDto.ok(commandFarmService.createFarm(createFarmRequestDto, userId));
    }

    @GetMapping("/getallfarm")
    public ResponseDto<?> getAllFarm(
        @UserId UUID userId
    ) {
        return ResponseDto.ok(queryFarmService.readFarm(userId));
    }

    @GetMapping("/getfarm")
    public ResponseDto<?> getFarm(
            @RequestParam(name = "farmID") Long farmId,
            @UserId UUID userId
    ) {
        return ResponseDto.ok(queryFarmService.readFarmDetail(farmId, userId));
    }

    @PatchMapping("/updatefarm")
    public ResponseDto<?> updateFarm(
            @RequestBody UpdateFarmRequestDto updateFarmRequestDto,
            @RequestParam(name = "farmID") Long farmId,
            @UserId UUID userId
    ) {
        return ResponseDto.ok(commandFarmService.updateFarm(updateFarmRequestDto, userId, farmId));
    }

    @DeleteMapping("/deletefarm")
    public ResponseDto<?> deleteFarm(
            @RequestParam(name = "farmID") Long farmId,
            @UserId UUID userId
    ) {
        return ResponseDto.ok(commandFarmService.deleteFarm(userId, farmId));
    }
}
