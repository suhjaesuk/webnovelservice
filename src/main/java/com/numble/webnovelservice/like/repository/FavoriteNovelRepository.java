package com.numble.webnovelservice.like.repository;

import com.numble.webnovelservice.like.entity.FavoriteNovel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteNovelRepository extends JpaRepository<FavoriteNovel, Long> {

    boolean existsByMemberIdAndNovelId(Long memberId, Long novelId);

    Optional<FavoriteNovel> findByMemberIdAndNovelId(Long memberId, Long novelId);

    List<FavoriteNovel> findByMemberId(Long memberId);
}
