package com.numble.webnovelservice.novel.repository;

import com.numble.webnovelservice.novel.entity.Genre;
import com.numble.webnovelservice.novel.entity.Novel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NovelRepository extends JpaRepository<Novel, Long> {

    List<Novel> findByGenre(Genre genre);

    List<Novel> findByTitleContainingOrAuthorContaining(String title, String author);

    List<Novel> findByOrderByUpdatedAtDesc();

    List<Novel> findByIdIn(List<Long> novelId);

    @Modifying
    @Query("UPDATE Novel n SET n.totalViewCount = n.totalViewCount + 1 WHERE n.id = :novelId")
    void increaseTotalViewCountAtomically(@Param("novelId") Long novelId);
}
