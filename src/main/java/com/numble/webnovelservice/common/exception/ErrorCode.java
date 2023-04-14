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

    INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "JWT_001", "Access Token이 유효하지 않습니다."),
    EXPIRATION_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "JWT_002", "Access Token이 만료되었습니다"),
    ACCESS_TOKEN_NOT_SUPPORT(HttpStatus.UNAUTHORIZED, "JWT_003", "지원하지 않는 Access Token입니다"),
    UNKNOWN_ACCESS_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "JWT_004", "Access Token 에러입니다"),
    UNKNOWN_TOKEN_ERROR(HttpStatus.BAD_REQUEST, "JWT_005", "알 수 없는 토큰 에러입니다"),

    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "MEMBER_001", "찾을 수 없는 회원입니다."),
    NOT_AUTHORIZED_MEMBER(HttpStatus.BAD_REQUEST, "MEMBER_002", "인가되지 않은 사용자입니다."),
    NOT_VALID_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER_003", "비밀번호를 다시 확인해주세요."),
    ALREADY_EXIST_USERNAME(HttpStatus.BAD_REQUEST, "MEMBER_004", "이미 존재하는 회원 아이디입니다."),
    ALREADY_EXIST_NICKNAME(HttpStatus.BAD_REQUEST, "MEMBER_005", "이미 존재하는 회원 닉네임입니다."),

    NOT_VALID_GENRE(HttpStatus.BAD_REQUEST, "NOVEL_001", "유효하지 않은 장르입니다."),
    NOT_VALID_SERIALIZED_STATUS(HttpStatus.BAD_REQUEST, "NOVEL_002", "유효하지 않은 연재 상태입니다."),
    NOT_FOUND_NOVEL(HttpStatus.NOT_FOUND, "NOVEL_003", "찾을 수 없는 소설입니다."),

    NOT_FOUND_EPISODE(HttpStatus.NOT_FOUND, "EPISODE_001", "찾을 수 없는 에피소드입니다."),

    INSUFFICIENT_POINT(HttpStatus.BAD_REQUEST, "POINT_TRANSACTION_001", "포인트가 부족합니다."),

    INSUFFICIENT_TICKET(HttpStatus.BAD_REQUEST, "TICKET_TRANSACTION_002", "소장권이 부족합니다."),

    NOT_FOUND_OWNED_EPISODE(HttpStatus.NOT_FOUND, "OWNED_EPISODE_001", "찾을 수 없는 소장 에피소드입니다."),
    PAGE_OUT_OF_BOUNDS(HttpStatus.BAD_REQUEST, "OWNED_EPISODE_002", "읽을 페이지가 페이지의 범위를 벗어났습니다."),
    DUPLICATE_OWNED_EPISODE(HttpStatus.BAD_REQUEST, "OWNED_EPISODE_003", "이미 등록된 소장 에피소드입니다."),

    DUPLICATE_FAVORITE_NOVEL(HttpStatus.BAD_REQUEST, "FAVORITE_NOVEL_001", "이미 등록된 선호 소설입니다."),
    NOT_FOUND_FAVORITE_NOVEL(HttpStatus.NOT_FOUND, "FAVORITE_NOVEL_002", "찾을 수 없는 선호 소설입니다."),

    DUPLICATE_HOME_EXPOSURE(HttpStatus.BAD_REQUEST, "HOME_EXPOSURE_001", "이미 등록된 홈 노출입니다."),
    NOT_FOUND_HOME_EXPOSURE(HttpStatus.NOT_FOUND, "HOME_EXPOSURE_002", "찾을 수 없는 홈 노출입니다."),
    HOME_EXPOSURE_COUNT_OUT_OF_BOUNDS(HttpStatus.BAD_REQUEST, "HOME_EXPOSURE_003", "홈 노출 저장 범위를 벗어났습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;
}
