package com.numble.webnovelservice.episode.dto.response;

import com.numble.webnovelservice.episode.entity.OwnedEpisode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EpisodeReadResponse {

    private String content;

    private Integer currentReadingPage;

    @Builder
    public EpisodeReadResponse(String content, Integer currentReadingPage) {

        this.content = content;
        this.currentReadingPage = currentReadingPage;
    }

    public static EpisodeReadResponse toResponse(OwnedEpisode ownedEpisode) {

        return EpisodeReadResponse.builder()
                .content(ownedEpisode.getEpisode().getContent())
                .currentReadingPage(ownedEpisode.getCurrentReadingPage())
                .build();
    }
}