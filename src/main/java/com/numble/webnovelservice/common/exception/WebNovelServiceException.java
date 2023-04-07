package com.numble.webnovelservice.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * WebNovelServiceException은 CustomException입니다.
 */
@Getter
@AllArgsConstructor
public class WebNovelServiceException extends RuntimeException{

    private final ErrorCode errorCode;
}
