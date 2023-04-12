package com.numble.webnovelservice.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {

    CHARGE("충전"),
    CONSUME("소모"),
    ;

    private final String koreanName;

    public static String toKoreanName(Type type) {

        return switch(type) {
            case CHARGE -> CHARGE.getKoreanName();
            case CONSUME -> CONSUME.getKoreanName();
        };
    }
}
