package com.numble.webnovelservice.homeexposure.repository;

import com.numble.webnovelservice.homeexposure.entity.HomeExposure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HomeExposureRepository extends JpaRepository<HomeExposure, Long> {

    boolean existsByNovelId(Long novelId);

    Optional<HomeExposure> findByNovelId(Long novelId);
}
