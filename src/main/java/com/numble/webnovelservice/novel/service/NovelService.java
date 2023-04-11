package com.numble.webnovelservice.novel.service;

import com.numble.webnovelservice.common.exception.WebNovelServiceException;
import com.numble.webnovelservice.novel.dto.request.NovelRegisterRequest;
import com.numble.webnovelservice.novel.dto.request.NovelUpdateInfoRequest;
import com.numble.webnovelservice.novel.dto.response.NovelInfoResponseList;
import com.numble.webnovelservice.novel.entity.Genre;
import com.numble.webnovelservice.novel.entity.Novel;
import com.numble.webnovelservice.novel.repository.NovelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        Novel novel = findNovelById(novelId);

        novel.updateInfo(request);
    }

    @Transactional
    public void deleteNovel(Long novelId){

        Novel novel = findNovelById(novelId);

        novelRepository.delete(novel);
    }

    private Novel findNovelById(Long novelId) {

        return novelRepository.findById(novelId).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_NOVEL));
    }

    @Transactional(readOnly = true)
    public NovelInfoResponseList retrieveAllNovels() {

        List<Novel> novels = novelRepository.findAll();

        return NovelInfoResponseList.toResponseList(novels);
    }

    @Transactional(readOnly = true)
    public NovelInfoResponseList retrieveNovelsByGenre(String koreanGenre) {

        Genre genre = Genre.fromKoreanName(koreanGenre);

        List<Novel> novels = novelRepository.findByGenre(genre);

        return NovelInfoResponseList.toResponseList(novels);
    }

    @Transactional(readOnly = true)
    public NovelInfoResponseList retrieveNovelsBySearch(String titleOrAuthor) {

        List<Novel> novels = novelRepository.findByTitleContainingOrAuthorContaining(titleOrAuthor, titleOrAuthor);

        return NovelInfoResponseList.toResponseList(novels);
    }

    @Transactional(readOnly = true)
    public NovelInfoResponseList retrieveLatestUpdateNovels() {

        List<Novel> novels = novelRepository.findByOrderByUpdatedAtDesc();

        return NovelInfoResponseList.toResponseList(novels);
    }
}
