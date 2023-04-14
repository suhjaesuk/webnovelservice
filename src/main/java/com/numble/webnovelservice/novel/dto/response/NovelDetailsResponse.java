package com.numble.webnovelservice.novel.dto.response;

import com.numble.webnovelservice.episode.dto.response.EpisodeDetailsResponse;
import com.numble.webnovelservice.episode.entity.Episode;
import com.numble.webnovelservice.episode.entity.OwnedEpisode;
import com.numble.webnovelservice.novel.entity.Genre;
import com.numble.webnovelservice.novel.entity.Novel;
import com.numble.webnovelservice.novel.entity.SerializedStatus;
import com.numble.webnovelservice.util.time.TimeConverter;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class NovelDetailsResponse {

    private Long novelId;

    private String title;

    private String author;

    private String genre;

    private String coverImage;

    private String serializedStatus;

    private Integer likeCount;

    private Integer totalViewCount;

    private String updatedAt;

    private List<EpisodeDetailsResponse> episodes;

    @Builder
    public NovelDetailsResponse(Long novelId, String title, String author, String genre, String coverImage, String serializedStatus, Integer likeCount, Integer totalViewCount, String updatedAt, List<EpisodeDetailsResponse> episodes) {

        this.novelId = novelId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.coverImage = coverImage;
        this.serializedStatus = serializedStatus;
        this.likeCount = likeCount;
        this.totalViewCount = totalViewCount;
        this.updatedAt = updatedAt;
        this.episodes = episodes;
    }

    public static NovelDetailsResponse toResponse(Novel novel,
                                                  List<Episode> episodes,
                                                  List<OwnedEpisode> currentMemberOwnedNovelEpisodes) {

        String koreanGenre = Genre.toKoreanName(novel.getGenre());
        String koreanSerializedStatus = SerializedStatus.toKoreanName(novel.getSerializedStatus());
        String convertedUpdatedAt = Optional.ofNullable(novel.getUpdatedAt())
                .map(TimeConverter::toStringFormat)
                .orElse(null);

        List<EpisodeDetailsResponse> responseList = episodes.stream()
                .map(episode -> EpisodeDetailsResponse.toResponse(
                        episode,
                        currentMemberOwnedNovelEpisodes.stream()
                                .filter(Objects::nonNull)
                                .filter(ownedEpisode -> ownedEpisode.getEpisode().getId().equals(episode.getId()))
                                .findFirst()
                                .orElse(null)))
                .collect(Collectors.toList());

        return NovelDetailsResponse.builder()
                .novelId(novel.getId())
                .title(novel.getTitle())
                .author(novel.getAuthor())
                .genre(koreanGenre)
                .coverImage(novel.getCoverImage())
                .serializedStatus(koreanSerializedStatus)
                .likeCount(novel.getLikeCount())
                .totalViewCount(novel.getTotalViewCount())
                .updatedAt(convertedUpdatedAt)
                .episodes(responseList)
                .build();
    }
}
