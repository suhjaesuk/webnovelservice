package com.numble.webnovelservice.novel.service;

import com.numble.webnovelservice.common.exception.WebNovelServiceException;
import com.numble.webnovelservice.novel.dto.request.NovelRegisterRequest;
import com.numble.webnovelservice.novel.dto.request.NovelUpdateInfoRequest;
import com.numble.webnovelservice.novel.entity.Novel;
import com.numble.webnovelservice.novel.repository.NovelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_NOVEL;

@Service
@RequiredArgsConstructor
public class NovelService {

    private final NovelRepository novelRepository;

    @Transactional
    public void registerNovel(NovelRegisterRequest request){

        Novel novel = request.toNovel();
        novelRepository.save(novel);
    }

    @Transactional
    public void updateNovelInfo(Long novelId, NovelUpdateInfoRequest request){

        Novel novel = novelRepository.findById(novelId).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_NOVEL));

        novel.updateInfo(request);
    }


}
