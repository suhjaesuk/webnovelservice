package com.numble.webnovelservice.novel.entity;

import com.numble.webnovelservice.common.exception.WebNovelServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_VALID_SERIALIZED_STATUS;

@Getter
@AllArgsConstructor
public enum SerializedStatus {

    SERIALIZING("연재 중"),
    PAUSED("휴재"),
    FINISHED("연재 완료"),
    ;

    private final String koreanName;

    public static SerializedStatus fromKoreanName(String serializedStatus) {

        return switch (serializedStatus) {
            case "연재 중" -> SERIALIZING;
            case "휴재" -> PAUSED;
            case "연재 완료" -> FINISHED;
            default -> throw new WebNovelServiceException(NOT_VALID_SERIALIZED_STATUS);
        };
    }

    public static String toKoreanName(SerializedStatus serializedStatus) {

        return switch (serializedStatus) {
            case SERIALIZING -> SERIALIZING.getKoreanName();
            case PAUSED -> PAUSED.getKoreanName();
            case FINISHED -> FINISHED.getKoreanName();
        };
    }
}
