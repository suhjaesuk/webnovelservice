package com.numble.webnovelservice.episode.service;

import com.numble.webnovelservice.common.exception.WebNovelServiceException;
import com.numble.webnovelservice.episode.dto.request.EpisodeRegisterRequest;
import com.numble.webnovelservice.episode.dto.request.EpisodeUpdateRequest;
import com.numble.webnovelservice.episode.entity.Episode;
import com.numble.webnovelservice.episode.repository.EpisodeRepository;
import com.numble.webnovelservice.novel.entity.Novel;
import com.numble.webnovelservice.novel.repository.NovelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_EPISODE;
import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_NOVEL;

@Service
@RequiredArgsConstructor
public class EpisodeService {

    private final EpisodeRepository episodeRepository;

    private final NovelRepository novelRepository;

    @Transactional
    public void registerEpisode(Long novelId, EpisodeRegisterRequest request) {

        Novel novel = findNovelById(novelId);

        novel.updateUpdatedAt();

        Episode episode = request.toEpisode(novel);
        episodeRepository.save(episode);
    }

    public void updateEpisode(Long novelId, Long episodeId, EpisodeUpdateRequest request) {

        findNovelById(novelId);

        Episode episode = findEpisodeById(episodeId);

        episode.updateInfo(request);
    }

    public void deleteEpisode(Long novelId, Long episodeId) {

        findNovelById(novelId);

        Episode episode = findEpisodeById(episodeId);

        episodeRepository.delete(episode);
    }

    private Novel findNovelById(Long novelId) {

        return novelRepository.findById(novelId).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_NOVEL)
        );
    }

    private Episode findEpisodeById(Long episodeId) {

        return episodeRepository.findById(episodeId).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_EPISODE)
        );
    }
}
