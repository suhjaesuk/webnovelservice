package com.numble.webnovelservice.dailybest.dto.response;

import com.numble.webnovelservice.novel.dto.response.NovelInfoResponse;
import com.numble.webnovelservice.novel.entity.Novel;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DailyBestNovelForPaidResponseList {

    private List<NovelInfoResponse> paidDailyBests;

    public DailyBestNovelForPaidResponseList(List<NovelInfoResponse> paidDailyBests) {

        this.paidDailyBests = paidDailyBests;
    }

    public static DailyBestNovelForPaidResponseList toResponse(List<Novel> novels) {

        List<NovelInfoResponse> responseList = novels.stream()
                .map(NovelInfoResponse::toResponse)
                .collect(Collectors.toList());

        return new DailyBestNovelForPaidResponseList(responseList);
    }
}
