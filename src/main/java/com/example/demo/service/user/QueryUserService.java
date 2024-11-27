package com.example.demo.service.user;

import com.example.demo.domain.User;
import com.example.demo.domain.type.ERole;
import com.example.demo.dto.request.user.LoginUserRequestDto;
import com.example.demo.dto.response.user.JwtTokenDto;
import com.example.demo.exception.CommonException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryUserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public JwtTokenDto userLogin(LoginUserRequestDto loginUserRequestDto) {
        User user = userRepository.findByEmail(loginUserRequestDto.email())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        if(!user.getPassword().equals(loginUserRequestDto.password())) {
            throw new CommonException(ErrorCode.BAD_REQUEST_PASSWORD);
        }

        return jwtUtil.generateTokens(user.getId(), ERole.USER);
    }
}
