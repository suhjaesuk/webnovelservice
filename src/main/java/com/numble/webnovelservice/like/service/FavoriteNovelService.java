package com.numble.webnovelservice.like.service;

import com.numble.webnovelservice.common.exception.WebNovelServiceException;
import com.numble.webnovelservice.like.dto.response.FavoriteNovelInfoResponseList;
import com.numble.webnovelservice.like.entity.FavoriteNovel;
import com.numble.webnovelservice.like.repository.FavoriteNovelRepository;
import com.numble.webnovelservice.member.entity.Member;
import com.numble.webnovelservice.novel.entity.Novel;
import com.numble.webnovelservice.novel.repository.NovelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.numble.webnovelservice.common.exception.ErrorCode.DUPLICATE_FAVORITE_NOVEL;
import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_FAVORITE_NOVEL;
import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_NOVEL;

@Service
@RequiredArgsConstructor
public class FavoriteNovelService {

    private final FavoriteNovelRepository favoriteNovelRepository;
    private final NovelRepository novelRepository;

    @Transactional
    public void registerFavoriteNovel(Member currentMember, Long novelId) {

        throwIfDuplicateFavoriteNovel(currentMember.getId(), novelId);

        Novel novel = novelRepository.findById(novelId).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_NOVEL)
        );

        FavoriteNovel favoriteNovel = FavoriteNovel.createFavoriteNovel(currentMember, novel);

        novel.increaseLikeCount();

        favoriteNovelRepository.save(favoriteNovel);
    }

    private void throwIfDuplicateFavoriteNovel(Long memberId, Long novelId) {

        if(favoriteNovelRepository.existsByMemberIdAndNovelId(memberId, novelId)){
            throw new WebNovelServiceException(DUPLICATE_FAVORITE_NOVEL);
        }
    }

    @Transactional
    public void removeFavoriteNovel(Member currentMember, Long novelId) {

        FavoriteNovel favoriteNovel = favoriteNovelRepository.
                findByMemberIdAndNovelId(currentMember.getId(), novelId).orElseThrow(
                        ()-> new WebNovelServiceException(NOT_FOUND_FAVORITE_NOVEL)
        );

        Novel novel = favoriteNovel.getNovel();

        novel.decreaseLikeCount();

        favoriteNovelRepository.delete(favoriteNovel);
    }

    @Transactional(readOnly = true)
    public FavoriteNovelInfoResponseList retrieveFavoriteNovelsByMember(Member currentMember){

        List<FavoriteNovel> favoriteNovels = favoriteNovelRepository.findByMemberId(currentMember.getId());

        return FavoriteNovelInfoResponseList.toResponse(favoriteNovels);
    }
}
