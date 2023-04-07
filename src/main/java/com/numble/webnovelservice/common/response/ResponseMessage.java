package com.numble.webnovelservice.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * ResponseMessage 클래스는 api 응답 형식입니다.
 * 아래는 형식의 예시 입니다.
 *
 * {
 * "message" :"소설 조회 성공",
 * "data" :
 *    {
 *    "novelId" : 1,
 *    "title" : "노인과 바다"
 *    }
 * }
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage<T> {

    private final String message;
    private final T data;

    public ResponseMessage(String message, T data){
        this.message = message;
        this.data = data;
    }
}
