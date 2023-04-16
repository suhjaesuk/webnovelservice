package com.numble.webnovelservice.dailybest.service;

import com.numble.webnovelservice.dailybest.dto.response.DailyBestNovelForFreeResponse;
import com.numble.webnovelservice.dailybest.dto.response.DailyBestNovelForPaidResponse;
import com.numble.webnovelservice.dailybest.dto.response.DailyBestNovelForAllPaymentTypeResponse;
import com.numble.webnovelservice.novel.entity.Novel;
import com.numble.webnovelservice.novel.repository.NovelRepository;
import com.numble.webnovelservice.util.redis.repository.DailyBestRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 거래 유형에 따른 일일 조회 베스트 조회
 * DailyBestNovelResponseForAllPaymentTypeList() : 유료 + 무료
 * DailyBestNovelResponseForAllPaidList() : 유료
 * DailyBestNovelResponseForAllFreeList() : 무료
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyBestService {

    private final NovelRepository novelRepository;
    private final DailyBestRedisRepository dailyBestRedisRepository;

    public DailyBestNovelForAllPaymentTypeResponse retrieveDailyBestNovelForAllPaymentType() {

        List<Long> novelsId = dailyBestRedisRepository.getDailyBestsNovelTitleForAllPaymentType();

        List<Novel> novels = novelRepository.findByIdIn(novelsId);

        return DailyBestNovelForAllPaymentTypeResponse.toResponse(novels);
    }

    public DailyBestNovelForPaidResponse retrieveDailyBestNovelForPaid() {

        List<Long> novelsId = dailyBestRedisRepository.getDailyBestsNovelTitleForPaid();

        List<Novel> novels = novelRepository.findByIdIn(novelsId);

        return DailyBestNovelForPaidResponse.toResponse(novels);
    }

    public DailyBestNovelForFreeResponse retrieveDailyBestNovelForFree() {

        List<Long> novelsId = dailyBestRedisRepository.getDailyBestsNovelTitleForFree();

        List<Novel> novels = novelRepository.findByIdIn(novelsId);

        return DailyBestNovelForFreeResponse.toResponse(novels);
    }
}
