package com.numble.webnovelservice.like.dto.response;

import com.numble.webnovelservice.like.entity.FavoriteNovel;
import com.numble.webnovelservice.novel.entity.Genre;
import com.numble.webnovelservice.novel.entity.Novel;
import com.numble.webnovelservice.novel.entity.SerializedStatus;
import com.numble.webnovelservice.util.time.TimeConverter;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
public class FavoriteNovelInfoResponse {

    private Long favoriteNovelId;

    private Long novelId;

    private String title;

    private String author;

    private String genre;

    private String coverImage;

    private String serializedStatus;

    private Integer likeCount;

    private Integer totalViewCount;

    private String updatedAt;

    @Builder
    public FavoriteNovelInfoResponse(Long favoriteNovelId, Long novelId, String title, String author, String genre, String coverImage, String serializedStatus, Integer likeCount, Integer totalViewCount, String updatedAt) {

        this.favoriteNovelId = favoriteNovelId;
        this.novelId = novelId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.coverImage = coverImage;
        this.serializedStatus = serializedStatus;
        this.likeCount = likeCount;
        this.totalViewCount = totalViewCount;
        this.updatedAt = updatedAt;
    }

    public static FavoriteNovelInfoResponse toResponse(FavoriteNovel favoriteNovel){

        Novel novel = favoriteNovel.getNovel();
        String koreanGenre = Genre.toKoreanName(novel.getGenre());
        String koreanSerializedStatus = SerializedStatus.toKoreanName(novel.getSerializedStatus());
        String convertedUpdatedAt = Optional.ofNullable(novel.getUpdatedAt())
                                            .map(TimeConverter::toStringFormat)
                                            .orElse(null);

        return FavoriteNovelInfoResponse.builder()
                .favoriteNovelId(favoriteNovel.getId())
                .novelId(novel.getId())
                .title(novel.getTitle())
                .author(novel.getAuthor())
                .genre(koreanGenre)
                .coverImage(novel.getCoverImage())
                .serializedStatus(koreanSerializedStatus)
                .likeCount(novel.getLikeCount())
                .totalViewCount(novel.getTotalViewCount())
                .updatedAt(convertedUpdatedAt)
                .build();
    }
}
