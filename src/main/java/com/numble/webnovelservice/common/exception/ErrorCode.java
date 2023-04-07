package com.numble.webnovelservice.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 이 클래스는 WebNovelServiceException 이라는 커스텀 예외 클래스에서 사용됩니다.
 * 예외 처리에 사용되는 HTTP 상태 코드, 메시지, 코드 등이 있습니다.
 * 이 정보들은 WebNovelServiceException 클래스에서 사용돼 예외를 생성하거나 예외 처리 결과를 생성하는 데 사용됩니다.
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;
}
