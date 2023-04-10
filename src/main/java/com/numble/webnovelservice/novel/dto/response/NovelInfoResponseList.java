package com.numble.webnovelservice.novel.dto.response;

import com.numble.webnovelservice.novel.entity.Novel;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class NovelInfoResponseList {

    private List<NovelInfoResponse> novels;

    public NovelInfoResponseList(List<NovelInfoResponse> novels) {

        this.novels = novels;
    }

    public static NovelInfoResponseList toResponseList(List<Novel> novels) {

        List<NovelInfoResponse> responseList = novels.stream()
                .map(NovelInfoResponse::toResponse)
                .collect(Collectors.toList());

        return new NovelInfoResponseList(responseList);
    }
}
