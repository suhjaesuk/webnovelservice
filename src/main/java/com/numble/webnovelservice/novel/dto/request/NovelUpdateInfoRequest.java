package com.numble.webnovelservice.novel.dto.request;

import lombok.Getter;

@Getter
public class NovelUpdateInfoRequest {

    private String description;

    private String coverImage;

    private String serializedStatus;
}
