package com.numble.webnovelservice.episode.repository;

import com.numble.webnovelservice.episode.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

   @Query("SELECT e FROM Episode e LEFT JOIN FETCH e.ownedEpisodes oe WHERE e.novel.id = :novelId")
   Optional<List<Episode>> findByNovelIdWithOwnedEpisodes(@Param("novelId") Long novelId);
}
