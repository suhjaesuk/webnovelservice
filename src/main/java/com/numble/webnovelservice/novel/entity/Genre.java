package com.numble.webnovelservice.novel.entity;

import com.numble.webnovelservice.common.exception.WebNovelServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_VALID_GENRE;

@Getter
@AllArgsConstructor
public enum Genre {

    COMEDY("코미디"),
    DRAMA("드라마"),
    FANTASY("판타지"),
    ROMANCE("로맨스"),
    SCIENCE_FICTION("SF"),
    ;

    private final String koreanName;

    public static Genre fromKoreanName(String koreanName) {

        return switch (koreanName) {
            case "코미디" -> COMEDY;
            case "드라마" -> DRAMA;
            case "판타지" -> FANTASY;
            case "로맨스" -> ROMANCE;
            case "SF" -> SCIENCE_FICTION;
            default -> throw new WebNovelServiceException(NOT_VALID_GENRE);
        };
    }

    public static String toKoreanName(Genre genre) {

        return switch (genre) {
            case COMEDY -> COMEDY.getKoreanName();
            case DRAMA -> DRAMA.getKoreanName();
            case FANTASY -> FANTASY.getKoreanName();
            case ROMANCE -> ROMANCE.getKoreanName();
            case SCIENCE_FICTION -> SCIENCE_FICTION.getKoreanName();
        };
    }
}
