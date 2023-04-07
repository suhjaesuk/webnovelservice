package com.numble.webnovelservice.util.security.exception;

import com.numble.webnovelservice.common.exception.ErrorCode;
import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, java.io.IOException {
        String exception = (String)request.getAttribute("exception");
        ErrorCode errorCode;

        if(exception == null) {
            errorCode = ErrorCode.NOT_AUTHORIZED_MEMBER;
        } else if (exception.equals(ErrorCode.EXPIRATION_ACCESS_TOKEN.getCode())) {
            errorCode = ErrorCode.EXPIRATION_ACCESS_TOKEN;
        } else if(exception.equals(ErrorCode.INVALID_ACCESS_TOKEN.getCode())) {
            errorCode = ErrorCode.INVALID_ACCESS_TOKEN;
        } else if(exception.equals(ErrorCode.ACCESS_TOKEN_NOT_SUPPORT.getCode())) {
            errorCode = ErrorCode.ACCESS_TOKEN_NOT_SUPPORT;
        } else if(exception.equals(ErrorCode.UNKNOWN_ACCESS_TOKEN_ERROR.getCode())) {
            errorCode = ErrorCode.UNKNOWN_ACCESS_TOKEN_ERROR;
        } else errorCode = ErrorCode.UNKNOWN_TOKEN_ERROR;

        setResponse(response, errorCode);
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException, java.io.IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.getWriter().println("{ \"message\" : \"" + errorCode.getMessage()
                + "\", \"code\" : \"" +  errorCode.getCode()
                + "\", \"status\" : " + errorCode.getHttpStatus()
                + ", \"errors\" : [ ] }");
    }
}