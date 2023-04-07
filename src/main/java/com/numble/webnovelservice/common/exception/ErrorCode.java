package com.numble.webnovelservice.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * ErrorCode는 GlobalExceptionHandler에 쓰입니다.
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;
}
