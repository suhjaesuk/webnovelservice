package com.numble.webnovelservice.homeexposure.dto.response;

import com.numble.webnovelservice.homeexposure.entity.HomeExposure;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class HomeExposureInfoResponseList {

    private List<HomeExposureInfoResponse> homeExposures;

    public HomeExposureInfoResponseList(List<HomeExposureInfoResponse> homeExposures) {

        this.homeExposures = homeExposures;
    }

    public static HomeExposureInfoResponseList toResponse(List<HomeExposure> homeExposures) {

        List<HomeExposureInfoResponse> responseList = homeExposures.stream()
                .map(HomeExposureInfoResponse::toResponse)
                .collect(Collectors.toList());

        return new HomeExposureInfoResponseList(responseList);

    }
}
