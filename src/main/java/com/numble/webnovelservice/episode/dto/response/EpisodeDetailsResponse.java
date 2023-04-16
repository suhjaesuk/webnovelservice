package com.numble.webnovelservice.episode.dto.response;

import com.numble.webnovelservice.episode.entity.Episode;
import com.numble.webnovelservice.ownedepisode.entity.OwnedEpisode;
import com.numble.webnovelservice.util.time.TimeConverter;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
public class EpisodeDetailsResponse {

    private Long episodeId;

    private String title;

    private Integer totalPageCount;

    private Float fileSize;

    private Boolean isFree;

    private Integer neededTicketCount;

    private Boolean isOwnedEpisode;

    private Boolean isRead;

    private String createdAt;

    @Builder
    public EpisodeDetailsResponse(Long episodeId, String title, Integer totalPageCount, Float fileSize, Boolean isFree, Integer neededTicketCount, Boolean isOwnedEpisode, Boolean isRead, String createdAt) {

        this.episodeId = episodeId;
        this.title = title;
        this.totalPageCount = totalPageCount;
        this.fileSize = fileSize;
        this.isFree = isFree;
        this.neededTicketCount = neededTicketCount;
        this.isOwnedEpisode = isOwnedEpisode;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public static EpisodeDetailsResponse toResponse(Episode episode, OwnedEpisode ownedEpisode) {

        String convertedCreatedAt = Optional.ofNullable(episode.getCreatedAt())
                .map(TimeConverter::toStringFormat)
                .orElse(null);

        boolean isOwnedEpisode = ownedEpisode != null;
        boolean isRead = Optional.ofNullable(ownedEpisode)
                .map(OwnedEpisode::getIsRead)
                .orElse(false);

        return EpisodeDetailsResponse.builder()
                .episodeId(episode.getId())
                .title(episode.getTitle())
                .totalPageCount(episode.getTotalPageCount())
                .fileSize(episode.getFileSize())
                .isFree(episode.getIsFree())
                .neededTicketCount(episode.getNeededTicketCount())
                .isOwnedEpisode(isOwnedEpisode)
                .isRead(isRead)
                .createdAt(convertedCreatedAt)
                .build();
    }
}
