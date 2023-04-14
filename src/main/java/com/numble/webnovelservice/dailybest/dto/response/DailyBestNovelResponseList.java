package com.numble.webnovelservice.dailybest.dto.response;

import com.numble.webnovelservice.novel.dto.response.NovelInfoResponse;
import com.numble.webnovelservice.novel.entity.Novel;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DailyBestNovelResponseList {

    private List<NovelInfoResponse> dailyBests;

    public DailyBestNovelResponseList(List<NovelInfoResponse> dailyBests) {

        this.dailyBests = dailyBests;
    }

    public static DailyBestNovelResponseList toResponse(List<Novel> novels) {

        List<NovelInfoResponse> responseList = novels.stream()
                .map(NovelInfoResponse::toResponse)
                .collect(Collectors.toList());

        return new DailyBestNovelResponseList(responseList);
    }
}
