package com.numble.webnovelservice.episode.repository;

import com.numble.webnovelservice.episode.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

   Optional<List<Episode>> findByNovelId(Long novelId);
}
