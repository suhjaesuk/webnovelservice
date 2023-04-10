package com.numble.webnovelservice.novel.repository;

import com.numble.webnovelservice.novel.entity.Novel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NovelRepository extends JpaRepository<Novel, Long> {
}
