package com.numble.webnovelservice.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ErrorMessage 클래스는 예외 발생 시 응답 형식입니다.
 * 아래는 형식의 예시 입니다.
 *
 * {
 * "message" :"접근 권한이 없습니다.",
 * "code" : "NOT_VALID_ACCESS"
 * }
 */
@Getter
@AllArgsConstructor
public class ErrorMessage {

    private String message;
    private String code;
}
