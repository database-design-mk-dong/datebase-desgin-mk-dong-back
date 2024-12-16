package com.example.demo.security.filter;

import com.example.demo.constant.Constants;
import com.example.demo.exception.CommonException;
import com.example.demo.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            // 다음 필터로 요청을 전달
            filterChain.doFilter(request, response);
        } catch (SecurityException e) {
            // 보안 예외 처리
            log.error("FilterException throw SecurityException Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.ACCESS_DENIED);
            filterChain.doFilter(request, response);
        } catch (MalformedJwtException e) {
            // 잘못된 형식의 JWT 예외 처리
            log.error("FilterException throw MalformedJwtException Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.TOKEN_MALFORMED_ERROR);
            filterChain.doFilter(request, response);
        } catch (IllegalArgumentException e) {
            // 잘못된 인자 예외 처리
            log.error("FilterException throw IllegalArgumentException Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.TOKEN_TYPE_ERROR);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            // 만료된 JWT 예외 처리
            log.error("FilterException throw ExpiredJwtException Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN_ERROR);
            filterChain.doFilter(request, response);
        } catch (UnsupportedJwtException e) {
            // 지원되지 않는 JWT 예외 처리
            log.error("FilterException throw UnsupportedJwtException Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.TOKEN_UNSUPPORTED_ERROR);
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            // 기타 JWT 관련 예외 처리
            log.error("FilterException throw JwtException Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.TOKEN_UNKNOWN_ERROR);
            filterChain.doFilter(request, response);
        } catch (CommonException e) {
            // 공통 예외 처리
            log.error("FilterException throw Exception Exception : {}", e.getMessage());
            request.setAttribute("exception", e.getErrorCode());
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            // 기타 모든 예외 처리
            log.error("FilterException throw Exception Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.INTERNAL_SERVER_ERROR);
            filterChain.doFilter(request, response);
        }
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
