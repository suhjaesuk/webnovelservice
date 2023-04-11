package com.numble.webnovelservice.episode.dto.request;

import lombok.Getter;

@Getter
public class EpisodeUpdateRequest {

    private String title;

    private String content;

    private Integer totalPageCount;

    private Integer neededTicketCount;

    private Float fileSize;


    public void setTitleForTest(String title) {
        this.title = title;
    }

    public void setContentForTest(String content) {
        this.content = content;
    }

    public void setTotalPageCountForTest(Integer totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public void setNeededTicketCountForTest(Integer neededTicketCount) {
        this.neededTicketCount = neededTicketCount;
    }

    public void setFileSizeForTest(Float fileSize) {
        this.fileSize = fileSize;
    }
}
