package com.numble.webnovelservice.episode.repository;

import com.numble.webnovelservice.episode.entity.OwnedEpisode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OwnedEpisodeRepository extends JpaRepository<OwnedEpisode, Long> {

    Optional<OwnedEpisode> findByMemberIdAndEpisodeId(Long memberId, Long episodeId);

    List<OwnedEpisode> findByMemberId(Long id);
}
