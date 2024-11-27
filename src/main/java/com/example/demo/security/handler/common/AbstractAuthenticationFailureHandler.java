package com.example.demo.security.handler.common;

import com.example.demo.dto.common.ExceptionDto;
import com.example.demo.exception.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.minidev.json.JSONValue;

public class AbstractAuthenticationFailureHandler {
    protected void setErrorResponse(
            HttpServletResponse response,
            ErrorCode errorCode) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getHttpStatus().value());

        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("data", null);
        result.put("error", ExceptionDto.of(errorCode));

        response.getWriter().write(JSONValue.toJSONString(result));
    }
}
