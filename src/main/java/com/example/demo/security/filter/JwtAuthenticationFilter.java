package com.example.demo.security.filter;

import com.example.demo.constant.Constants;
import com.example.demo.domain.type.ERole;
import com.example.demo.exception.CommonException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.security.info.UserPrincipal;
import com.example.demo.security.usecase.LoadUserPrincipalByIdUseCase;
import com.example.demo.util.HeaderUtil;
import com.example.demo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final LoadUserPrincipalByIdUseCase loadUserPrincipalByIdUseCase;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 요청 헤더에서 JWT 토큰을 추출하고, 없으면 예외를 발생시킴
        String token = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER_ERROR));

        // JWT 토큰을 검증하고 클레임을 추출
        Claims claims = jwtUtil.validateToken(token);

        // 클레임에서 사용자 ID와 역할을 추출
        UUID userId = UUID.fromString(claims.get(Constants.USER_ID_CLAIM_NAME, String.class));
        ERole role = ERole.fromName(claims.get(Constants.USER_ROLE_CLAIM_NAME, String.class));

        // 사용자 ID를 이용해 UserPrincipal 객체를 로드
        UserPrincipal userPrincipal = (UserPrincipal) loadUserPrincipalByIdUseCase.execute(userId);

        // 토큰의 역할과 사용자의 실제 역할이 일치하는지 확인
        if (!userPrincipal.getRole().equals(role)) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        // 인증 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userPrincipal, null, userPrincipal.getAuthorities());

        // 인증 토큰에 요청 세부 정보 설정
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // SecurityContext 생성 및 인증 정보 설정
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 현재 요청 URI 가져오기
        String requestURI = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        // 인증이 필요 없는 URL 패턴과 현재 요청 URI를 비교
        // 일치하는 패턴이 있으면 이 필터를 적용하지 않음
        return Constants.NO_NEED_AUTH_URLS.stream()
                .anyMatch(pattern -> antPathMatcher.match(pattern, requestURI));
    }
}

