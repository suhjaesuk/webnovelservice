package com.numble.webnovelservice.homeexposure.service;

import com.numble.webnovelservice.common.exception.WebNovelServiceException;
import com.numble.webnovelservice.homeexposure.dto.response.HomeExposureInfoResponseList;
import com.numble.webnovelservice.homeexposure.entity.HomeExposure;
import com.numble.webnovelservice.homeexposure.repository.HomeExposureRepository;
import com.numble.webnovelservice.novel.entity.Novel;
import com.numble.webnovelservice.novel.repository.NovelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.numble.webnovelservice.common.exception.ErrorCode.DUPLICATE_HOME_EXPOSURE;
import static com.numble.webnovelservice.common.exception.ErrorCode.HOME_EXPOSURE_COUNT_OUT_OF_BOUNDS;
import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_HOME_EXPOSURE;
import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_NOVEL;

@Service
@RequiredArgsConstructor
public class HomeExposureService {

    private final HomeExposureRepository homeExposureRepository;
    private final NovelRepository novelRepository;

    @Transactional
    public void registerHomeExposure(Long novelId) {

        throwIfDuplicateHomeExposure(novelId);
        throwIfInvalidHomeExposureCount();

        Novel novel = novelRepository.findById(novelId).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_NOVEL)
        );

        HomeExposure homeExposure = HomeExposure.createHomeExposure(novel);

        homeExposureRepository.save(homeExposure);
    }

    private void throwIfDuplicateHomeExposure(Long novelId) {

        if(homeExposureRepository.existsByNovelId(novelId)){
            throw new WebNovelServiceException(DUPLICATE_HOME_EXPOSURE);
        }
    }

    private void throwIfInvalidHomeExposureCount() {

        final int MAX_HOME_EXPOSURE_COUNT = 10;

        if (novelRepository.count() >= MAX_HOME_EXPOSURE_COUNT) {
            throw new WebNovelServiceException(HOME_EXPOSURE_COUNT_OUT_OF_BOUNDS);
        }
    }

    @Transactional
    public void removeHomeExposure(Long novelId) {

        HomeExposure homeExposure = homeExposureRepository.findByNovelId(novelId).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_HOME_EXPOSURE)
        );

        homeExposureRepository.delete(homeExposure);
    }

    @Transactional(readOnly = true)
    public HomeExposureInfoResponseList retrieveAllHomeExposures() {

        List<HomeExposure> homeExposures = homeExposureRepository.findAll();

        return HomeExposureInfoResponseList.toResponse(homeExposures);
    }
}
