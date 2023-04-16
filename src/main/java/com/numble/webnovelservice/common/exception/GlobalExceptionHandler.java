package com.numble.webnovelservice.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *  예외가 발생할 시 해당 예외에 대한 HTTP 상태 코드와 메시지를 생성하여 ResponseEntity 객체로 반환합니다.
 *  반환된 객체는 클라이언트에게 전송되어 예외 처리 결과를 알려줍니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = WebNovelServiceException.class)
    public ResponseEntity<?> handleWebNovelServiceException(WebNovelServiceException e){

        logger.error(e.getErrorCode().getMessage() + " : " + e.getErrorCode().getCode());
        HttpStatus status = e.getErrorCode().getHttpStatus();
        ErrorMessage error = new ErrorMessage(e.getErrorCode().getCode(), e.getErrorCode().getMessage());

        return ResponseEntity.status(status).body(error);
    }
}
