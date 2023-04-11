package com.numble.webnovelservice.novel.dto.request;

import lombok.Getter;

@Getter
public class NovelUpdateInfoRequest {

    private String description;

    private String coverImage;

    private String serializedStatus;

    public void setDescriptionForTest(String description) {
        this.description = description;
    }

    public void setCoverImageForTest(String coverImage) {
        this.coverImage = coverImage;
    }

    public void setSerializedStatusForTest(String serializedStatus) {
        this.serializedStatus = serializedStatus;
    }
}
