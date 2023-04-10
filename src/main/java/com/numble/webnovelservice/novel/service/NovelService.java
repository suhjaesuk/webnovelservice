package com.numble.webnovelservice.novel.service;

import com.numble.webnovelservice.novel.dto.request.NovelRegisterRequest;
import com.numble.webnovelservice.novel.entity.Novel;
import com.numble.webnovelservice.novel.repository.NovelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NovelService {

    private final NovelRepository novelRepository;

    @Transactional
    public void registerNovel(NovelRegisterRequest request){

        Novel novel = request.toNovel();
        novelRepository.save(novel);
    }
}
