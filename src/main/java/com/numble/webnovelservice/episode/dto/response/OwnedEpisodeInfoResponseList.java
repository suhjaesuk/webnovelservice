package com.numble.webnovelservice.episode.dto.response;

import com.numble.webnovelservice.episode.entity.OwnedEpisode;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OwnedEpisodeInfoResponseList {

    private List<OwnedEpisodeInfoResponse> ownedEpisodes;

    public OwnedEpisodeInfoResponseList(List<OwnedEpisodeInfoResponse> ownedEpisodeInfoResponses) {

        this.ownedEpisodes = ownedEpisodeInfoResponses;
    }

    public static OwnedEpisodeInfoResponseList toResponse(List<OwnedEpisode> ownedEpisodes) {

        List<OwnedEpisodeInfoResponse> responseList = ownedEpisodes.stream()
                .map(OwnedEpisodeInfoResponse::toResponse)
                .collect(Collectors.toList());

        return new OwnedEpisodeInfoResponseList(responseList);
    }
}
