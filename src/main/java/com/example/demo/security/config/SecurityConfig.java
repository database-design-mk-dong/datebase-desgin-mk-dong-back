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

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomSignOutProcessHandler customSignOutProcessHandler;
    private final CustomSignOutResultHandler customSignOutResultHandler;

    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final LoadUserPrincipalByIdUseCase loadUserPrincipalByIdUseCase;
    private final JwtUtil jwtUtil;


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // 허용할 Origin 추가
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(registry ->
                        registry
//                                .anyRequest().permitAll()
                                .requestMatchers(Constants.NO_NEED_AUTH_URLS.toArray(String[]::new)).permitAll()
                                .requestMatchers(Constants.ADMIN_URLS.toArray(String[]::new)).hasRole("ADMIN")
                                .requestMatchers(Constants.USER_URLS.toArray(String[]::new)).hasAnyRole("USER", "ADMIN")
                                .anyRequest().authenticated()
                )

                .formLogin(AbstractHttpConfigurer::disable)

                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .addLogoutHandler(customSignOutProcessHandler)
                        .logoutSuccessHandler(customSignOutResultHandler)
                )

                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .authenticationEntryPoint(jwtAuthEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )

                .addFilterBefore(
                        new JwtAuthenticationFilter(loadUserPrincipalByIdUseCase, jwtUtil),
                        LogoutFilter.class)
                .addFilterBefore(
                        new JwtExceptionFilter(),
                        JwtAuthenticationFilter.class)

                .getOrBuild();
    }
}
