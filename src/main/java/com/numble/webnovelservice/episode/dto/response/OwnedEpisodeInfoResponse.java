package com.numble.webnovelservice.episode.dto.response;

import com.numble.webnovelservice.episode.entity.Episode;
import com.numble.webnovelservice.episode.entity.OwnedEpisode;
import com.numble.webnovelservice.novel.entity.Novel;
import com.numble.webnovelservice.util.time.TimeConverter;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
public class OwnedEpisodeInfoResponse {

    private Long ownedEpisodeId;

    private String novelTitle;

    private String coverImage;

    private String episodeTitle;

    private String createdAt;

    @Builder
    public OwnedEpisodeInfoResponse(Long ownedEpisodeId, String novelTitle, String coverImage, String episodeTitle, String createdAt) {
        this.ownedEpisodeId = ownedEpisodeId;
        this.novelTitle = novelTitle;
        this.coverImage = coverImage;
        this.episodeTitle = episodeTitle;
        this.createdAt = createdAt;
    }

    public static OwnedEpisodeInfoResponse toResponse(OwnedEpisode ownedEpisode){

        Episode episode = ownedEpisode.getEpisode();
        Novel novel = episode.getNovel();
        String convertedCreatedAt = Optional.ofNullable(ownedEpisode.getCreatedAt())
                                            .map(TimeConverter::toStringFormat)
                                            .orElse(null);

        return OwnedEpisodeInfoResponse.builder()
                .ownedEpisodeId(ownedEpisode.getId())
                .novelTitle(novel.getTitle())
                .coverImage(novel.getCoverImage())
                .episodeTitle(episode.getTitle())
                .createdAt(convertedCreatedAt)
                .build();
    }
}
