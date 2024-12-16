package com.example.demo.security.config;

import com.example.demo.constant.Constants;
import com.example.demo.security.filter.JwtAuthenticationFilter;
import com.example.demo.security.filter.JwtExceptionFilter;
import com.example.demo.security.handler.jwt.JwtAccessDeniedHandler;
import com.example.demo.security.handler.jwt.JwtAuthEntryPoint;
import com.example.demo.security.handler.logout.CustomSignOutProcessHandler;
import com.example.demo.security.handler.logout.CustomSignOutResultHandler;
import com.example.demo.security.usecase.LoadUserPrincipalByIdUseCase;
import com.example.demo.util.JwtUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration // 이 클래스가 Spring 구성 클래스임을 나타냄
@EnableWebSecurity // Spring Security를 활성화
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 생성
public class SecurityConfig {
    private final CustomSignOutProcessHandler customSignOutProcessHandler;
    private final CustomSignOutResultHandler customSignOutResultHandler;

    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final LoadUserPrincipalByIdUseCase loadUserPrincipalByIdUseCase;
    private final JwtUtil jwtUtil;



    @Bean
    protected SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 인증 비활성화
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ) // 세션을 사용하지 않고 상태를 저장하지 않는 설정

                .authorizeHttpRequests(registry ->
                        registry
                                .requestMatchers(Constants.NO_NEED_AUTH_URLS.toArray(String[]::new)).permitAll() // 인증이 필요 없는 URL 설정
                                .requestMatchers(Constants.ADMIN_URLS.toArray(String[]::new)).hasRole("ADMIN") // 관리자 권한이 필요한 URL 설정
                                .requestMatchers(Constants.USER_URLS.toArray(String[]::new)).hasAnyRole("USER", "ADMIN") // 사용자 또는 관리자 권한이 필요한 URL 설정
                                .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                )

                .formLogin(AbstractHttpConfigurer::disable) // 폼 로그인 비활성화

                .logout(logout -> logout
                        .logoutUrl("/auth/logout") // 로그아웃 URL 설정
                        .addLogoutHandler(customSignOutProcessHandler) // 커스텀 로그아웃 처리기 추가
                        .logoutSuccessHandler(customSignOutResultHandler) // 로그아웃 성공 처리기 설정
                )

                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .authenticationEntryPoint(jwtAuthEntryPoint) // 인증 실패 시 처리할 엔트리 포인트 설정
                                .accessDeniedHandler(jwtAccessDeniedHandler) // 접근 거부 시 처리할 핸들러 설정
                )

                .addFilterBefore(
                        new JwtAuthenticationFilter(loadUserPrincipalByIdUseCase, jwtUtil),
                        LogoutFilter.class) // JWT 인증 필터 추가
                .addFilterBefore(
                        new JwtExceptionFilter(),
                        JwtAuthenticationFilter.class) // JWT 예외 처리 필터 추가

                .getOrBuild(); // SecurityFilterChain 빌드 및 반환
    }
}
