package com.numble.webnovelservice.novel.service;

import com.numble.webnovelservice.common.exception.WebNovelServiceException;
import com.numble.webnovelservice.episode.entity.Episode;
import com.numble.webnovelservice.episode.entity.OwnedEpisode;
import com.numble.webnovelservice.episode.repository.EpisodeRepository;
import com.numble.webnovelservice.member.entity.Member;
import com.numble.webnovelservice.novel.dto.request.NovelRegisterRequest;
import com.numble.webnovelservice.novel.dto.request.NovelUpdateInfoRequest;
import com.numble.webnovelservice.novel.dto.response.NovelDetailsResponse;
import com.numble.webnovelservice.novel.dto.response.NovelInfoResponseList;
import com.numble.webnovelservice.novel.entity.Genre;
import com.numble.webnovelservice.novel.entity.Novel;
import com.numble.webnovelservice.novel.repository.NovelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_EPISODE;
import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_NOVEL;

@Service
@RequiredArgsConstructor
public class NovelService {

    private final NovelRepository novelRepository;
    private final EpisodeRepository episodeRepository;

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

    @Transactional(readOnly = true)
    public NovelDetailsResponse retrieveNovelDetails(Member currentMember, Long novelId) {

        Novel novel = findNovelById(novelId);

        List<Episode> episodes= episodeRepository.findByNovelId(novelId).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_EPISODE)
        );

        List<OwnedEpisode> currentMemberOwnedNovelEpisodes = getCurrentMemberOwnedNovelEpisodes(currentMember, episodes);

        return NovelDetailsResponse.toResponse(novel, episodes, currentMemberOwnedNovelEpisodes);
    }

    /**
     * episode 의 ownedEpisodes 에 회원과 일치하는 ownedEpisode 가 있다면,
     * 리스트에 값을 넣는다.
     * episode 의 ownedEpisodes 가 null 이거나 회원과 일치하는 ownedEpisode 가 없다면,
     * 리스트에 null을 넣는다.
     *
     * @param currentMember
     * @param episodes
     * @return currentMemberOwnedNovelEpisodes = [null, ownedEpisode1, ownedEpisode2, null, ownedEpisode4]
     *
     */
    private List<OwnedEpisode> getCurrentMemberOwnedNovelEpisodes(Member currentMember, List<Episode> episodes) {

        return episodes.stream()
                .map(episode -> episode.getOwnedEpisodes()
                        .stream()
                        .filter(ownedEpisode -> ownedEpisode.getMember().equals(currentMember))
                        .findFirst()
                        .orElse(null))
                .collect(Collectors.toList());
    }

    private Novel findNovelById(Long novelId) {

        return novelRepository.findById(novelId).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_NOVEL));
    }
}
