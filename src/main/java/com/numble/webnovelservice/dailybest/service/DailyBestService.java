package com.numble.webnovelservice.dailybest.service;

import com.numble.webnovelservice.dailybest.dto.response.DailyBestNovelForFreeResponseList;
import com.numble.webnovelservice.dailybest.dto.response.DailyBestNovelForPaidResponseList;
import com.numble.webnovelservice.dailybest.dto.response.DailyBestNovelResponseList;
import com.numble.webnovelservice.novel.entity.Novel;
import com.numble.webnovelservice.novel.repository.NovelRepository;
import com.numble.webnovelservice.util.redis.repository.DailyBestRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyBestService {

    private final NovelRepository novelRepository;
    private final DailyBestRedisRepository dailyBestRedisRepository;

    public DailyBestNovelResponseList retrieveDailyBestNovelForAllPaymentType() {

        List<String> novelsTitle = dailyBestRedisRepository.getDailyBestsNovelTitleForAllPaymentType();

        List<Novel> novels = novelRepository.findByTitleIn(novelsTitle);

        return DailyBestNovelResponseList.toResponse(novels);
    }

    public DailyBestNovelForPaidResponseList retrieveDailyBestNovelForPaid() {

        List<String> novelsTitle = dailyBestRedisRepository.getDailyBestsNovelTitleForPaid();

        List<Novel> novels = novelRepository.findByTitleIn(novelsTitle);

        return DailyBestNovelForPaidResponseList.toResponse(novels);
    }

    public DailyBestNovelForFreeResponseList retrieveDailyBestNovelForFree() {

        List<String> novelsTitle = dailyBestRedisRepository.getDailyBestsNovelTitleForFree();

        List<Novel> novels = novelRepository.findByTitleIn(novelsTitle);

        return DailyBestNovelForFreeResponseList.toResponse(novels);
    }
}
