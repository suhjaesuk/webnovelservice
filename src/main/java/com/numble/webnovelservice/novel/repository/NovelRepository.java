package com.numble.webnovelservice.novel.repository;

import com.numble.webnovelservice.novel.entity.Genre;
import com.numble.webnovelservice.novel.entity.Novel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NovelRepository extends JpaRepository<Novel, Long> {

    List<Novel> findByGenre(Genre genre);

    List<Novel> findByTitleContainingOrAuthorContaining(String title, String author);

    List<Novel> findByOrderByUpdatedAtDesc();
}
