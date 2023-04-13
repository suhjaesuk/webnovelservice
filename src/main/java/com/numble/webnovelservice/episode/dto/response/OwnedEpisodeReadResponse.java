package com.numble.webnovelservice.episode.dto.response;

import com.numble.webnovelservice.episode.entity.OwnedEpisode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OwnedEpisodeReadResponse {

    private String content;

    private Integer currentReadingPage;

    @Builder
    public OwnedEpisodeReadResponse(String content, Integer currentReadingPage) {

        this.content = content;
        this.currentReadingPage = currentReadingPage;
    }

    public static OwnedEpisodeReadResponse toResponse(OwnedEpisode ownedEpisode) {

        return OwnedEpisodeReadResponse.builder()
                .content(ownedEpisode.getEpisode().getContent())
                .currentReadingPage(ownedEpisode.getCurrentReadingPage())
                .build();
    }
}