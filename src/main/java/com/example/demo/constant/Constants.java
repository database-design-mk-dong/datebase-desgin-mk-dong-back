package com.example.demo.constant;

import java.util.List;

public class Constants {
    public static String USER_ID_ATTRIBUTE_NAME = "USER_ID";
    public static String USER_ID_CLAIM_NAME = "uid";
    public static String USER_ROLE_CLAIM_NAME = "rol";
    public static String BEARER_PREFIX = "Bearer ";
    public static String AUTHORIZATION_HEADER = "Authorization";

    public static List<String> NO_NEED_AUTH_URLS = List.of(
            "/users/**",
            "/auth/reissue",
            "/oauth/login/kakao",
            "/oauth/login/kakao/callback",

            "/api-docs.html",
            "/api-docs/**",

            "/favicon.ico",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**",

            "/api/health"
    );

    public static List<String> USER_URLS = List.of(
            "/**");

    public static List<String> ADMIN_URLS = List.of(
            "/admin/**");
}
