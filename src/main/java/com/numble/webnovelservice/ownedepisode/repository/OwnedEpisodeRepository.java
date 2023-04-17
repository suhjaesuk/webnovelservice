package com.numble.webnovelservice.ownedepisode.repository;

import com.numble.webnovelservice.ownedepisode.entity.OwnedEpisode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OwnedEpisodeRepository extends JpaRepository<OwnedEpisode, Long> {

    @Query("SELECT oe FROM OwnedEpisode oe JOIN FETCH oe.episode e WHERE oe.member.id = :memberId AND oe.episode.id = :episodeId")
    Optional<OwnedEpisode> findByMemberIdAndEpisodeIdWithEpisode(@Param("memberId") Long memberId, @Param("episodeId") Long episodeId);

    @Query("SELECT oe FROM OwnedEpisode oe JOIN FETCH oe.episode e JOIN FETCH e.novel n WHERE oe.member.id = :memberId AND oe.episode.id = :episodeId")
    Optional<OwnedEpisode> findByMemberIdAndEpisodeIdWithEpisodeAndNovel(@Param("memberId") Long memberId, @Param("episodeId") Long episodeId);

    @Query("SELECT oe FROM OwnedEpisode oe JOIN FETCH oe.episode ep JOIN FETCH ep.novel WHERE oe.member.id = :memberId")
    List<OwnedEpisode> findByMemberIdWithEpisodeAndNovel(@Param("memberId") Long memberId);

    boolean existsByMemberIdAndEpisodeId(Long memberId, Long episodeId);
}
