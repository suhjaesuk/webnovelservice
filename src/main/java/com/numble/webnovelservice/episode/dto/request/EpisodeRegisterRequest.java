package com.numble.webnovelservice.episode.dto.request;

import com.numble.webnovelservice.episode.entity.Episode;
import com.numble.webnovelservice.novel.entity.Novel;
import lombok.Getter;

@Getter
public class EpisodeRegisterRequest {

    private String title;

    private String content;

    private Integer totalPageCount;

    private Integer neededTicketCount;

    private Float fileSize;

    public Episode toEpisode(Novel novel){
        boolean isFree = (neededTicketCount == 0);

        return Episode.builder()
                .title(title)
                .content(content)
                .totalPageCount(totalPageCount)
                .isFree(isFree)
                .neededTicketCount(neededTicketCount)
                .fileSize(fileSize)
                .viewCount(0)
                .novel(novel)
                .build();
    }
}
