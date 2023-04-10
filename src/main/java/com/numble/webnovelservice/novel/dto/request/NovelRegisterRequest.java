package com.numble.webnovelservice.novel.dto.request;

import com.numble.webnovelservice.novel.entity.Genre;
import com.numble.webnovelservice.novel.entity.Novel;
import com.numble.webnovelservice.novel.entity.SerializedStatus;
import lombok.Getter;

@Getter
public class NovelRegisterRequest {

    private String title;

    private String description;

    private String author;

    private String genre;

    private String serializedStatus;

    private String coverImage;

    public Novel toNovel(){
        Genre genreEnum = Genre.fromKoreanName(genre);
        SerializedStatus serializedStatusEnum = SerializedStatus.fromKoreanName(serializedStatus);

        return Novel.builder()
                .title(title)
                .description(description)
                .author(author)
                .genre(genreEnum)
                .serializedStatus(serializedStatusEnum)
                .coverImage(coverImage)
                .totalViewCount(0)
                .build();
    }
}
