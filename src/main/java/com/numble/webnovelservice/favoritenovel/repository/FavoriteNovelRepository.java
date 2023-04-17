package com.numble.webnovelservice.favoritenovel.repository;

import com.numble.webnovelservice.favoritenovel.entity.FavoriteNovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoriteNovelRepository extends JpaRepository<FavoriteNovel, Long> {

    boolean existsByMemberIdAndNovelId(Long memberId, Long novelId);

    @Query("SELECT fn FROM FavoriteNovel fn JOIN FETCH fn.novel n WHERE fn.member.id =:memberId AND fn.novel.id =:novelId")
    Optional<FavoriteNovel> findByMemberIdAndNovelIdWithNovel(@Param("memberId") Long memberId, @Param("novelId") Long novelId);

    @Query("SELECT fn FROM FavoriteNovel fn JOIN FETCH fn.novel n WHERE fn.member.id =:memberId")
    List<FavoriteNovel> findByMemberIdWithNovel(@Param("memberId") Long memberId);
}