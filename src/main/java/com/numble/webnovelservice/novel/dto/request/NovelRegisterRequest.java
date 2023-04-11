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
                .likeCount(0)
                .build();
    }

    public void setTitleForTest(String title) {
        this.title = title;
    }

    public void setDescriptionForTest(String description) {
        this.description = description;
    }

    public void setAuthorForTest(String author) {
        this.author = author;
    }

    public void setGenreForTest(String genre) {
        this.genre = genre;
    }

    public void setSerializedStatusForTest(String serializedStatus) {
        this.serializedStatus = serializedStatus;
    }

    public void setCoverImageForTest(String coverImage) {
        this.coverImage = coverImage;
    }
}
