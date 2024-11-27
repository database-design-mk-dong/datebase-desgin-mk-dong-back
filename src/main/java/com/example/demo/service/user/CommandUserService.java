package com.example.demo.service.user;

import com.example.demo.domain.User;
import com.example.demo.domain.type.ERole;
import com.example.demo.dto.request.user.CreateUserRequestDto;
import com.example.demo.dto.request.user.LoginUserRequestDto;
import com.example.demo.dto.response.user.JwtTokenDto;
import com.example.demo.exception.CommonException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommandUserService {
    private final UserRepository userRepository;

    public Boolean userRegister(CreateUserRequestDto createUserRequestDto) {
        if(userRepository.existsByEmail(createUserRequestDto.email())) {
            throw new CommonException(ErrorCode.EXIST_BY_USERNAME);
        }

        User user = User.builder()
                .email(createUserRequestDto.email())
                .password(createUserRequestDto.password())
                .role(ERole.USER)
                .nickname(createUserRequestDto.username())
                .build();
        userRepository.save(user);
        return true;
    }



}
