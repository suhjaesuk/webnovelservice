package com.numble.webnovelservice.dailybest.dto.response;

import com.numble.webnovelservice.novel.dto.response.NovelInfoResponse;
import com.numble.webnovelservice.novel.entity.Novel;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DailyBestNovelForAllPaymentTypeResponse {

    private List<NovelInfoResponse> dailyBestsForAllPaymentType;

    public DailyBestNovelForAllPaymentTypeResponse(List<NovelInfoResponse> dailyBestsForAllPaymentType) {

        this.dailyBestsForAllPaymentType = dailyBestsForAllPaymentType;
    }

    public static DailyBestNovelForAllPaymentTypeResponse toResponse(List<Novel> novels) {

        List<NovelInfoResponse> responseList = novels.stream()
                .map(NovelInfoResponse::toResponse)
                .collect(Collectors.toList());

        return new DailyBestNovelForAllPaymentTypeResponse(responseList);
    }
}
