package com.numble.webnovelservice.episode.dto.request;

import lombok.Getter;

@Getter
public class EpisodeUpdateRequest {

    private String title;

    private String content;

    private Integer totalPageCount;

    private Integer neededTicketCount;

    private Float fileSize;
}
