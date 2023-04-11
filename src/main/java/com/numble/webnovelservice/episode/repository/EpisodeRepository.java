package com.numble.webnovelservice.episode.repository;

import com.numble.webnovelservice.episode.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
}
