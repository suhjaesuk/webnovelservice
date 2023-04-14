package com.numble.webnovelservice.dailybest.dto.response;

import com.numble.webnovelservice.novel.dto.response.NovelInfoResponse;
import com.numble.webnovelservice.novel.entity.Novel;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DailyBestNovelForFreeResponseList {

    private List<NovelInfoResponse> freeDailyBests;

    public DailyBestNovelForFreeResponseList(List<NovelInfoResponse> freeDailyBests) {

        this.freeDailyBests = freeDailyBests;
    }

    public static DailyBestNovelForFreeResponseList toResponse(List<Novel> novels) {

        List<NovelInfoResponse> responseList = novels.stream()
                .map(NovelInfoResponse::toResponse)
                .collect(Collectors.toList());

        return new DailyBestNovelForFreeResponseList(responseList);
    }
}
