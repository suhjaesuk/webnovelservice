package com.numble.webnovelservice.dailybest.dto.response;

import com.numble.webnovelservice.novel.dto.response.NovelInfoResponse;
import com.numble.webnovelservice.novel.entity.Novel;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DailyBestNovelForFreeResponse {

    private List<NovelInfoResponse> DailyBestNovelsForFree;

    public DailyBestNovelForFreeResponse(List<NovelInfoResponse> DailyBestNovelsForFree) {
        this.DailyBestNovelsForFree = DailyBestNovelsForFree;
    }

    public static DailyBestNovelForFreeResponse toResponse(List<Novel> novels) {

        List<NovelInfoResponse> responseList = novels.stream()
                .map(NovelInfoResponse::toResponse)
                .collect(Collectors.toList());

        return new DailyBestNovelForFreeResponse(responseList);
    }
}
