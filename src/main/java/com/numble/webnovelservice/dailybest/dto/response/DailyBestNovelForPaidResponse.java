package com.numble.webnovelservice.dailybest.dto.response;

import com.numble.webnovelservice.novel.dto.response.NovelInfoResponse;
import com.numble.webnovelservice.novel.entity.Novel;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DailyBestNovelForPaidResponse {

    private List<NovelInfoResponse> DailyBestNovelsForPaid;

    public DailyBestNovelForPaidResponse(List<NovelInfoResponse> DailyBestNovelsForPaid) {

        this.DailyBestNovelsForPaid = DailyBestNovelsForPaid;
    }

    public static DailyBestNovelForPaidResponse toResponse(List<Novel> novels) {

        List<NovelInfoResponse> responseList = novels.stream()
                .map(NovelInfoResponse::toResponse)
                .collect(Collectors.toList());

        return new DailyBestNovelForPaidResponse(responseList);
    }
}
