package com.numble.webnovelservice.homeexposure.entity;

import com.numble.webnovelservice.novel.entity.Novel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HomeExposure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="favorite_novel_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="novel_id")
    private Novel novel;

    @Builder
    public HomeExposure(Long id, Novel novel) {

        this.id = id;
        this.novel = novel;
    }

    public static HomeExposure createHomeExposure(Novel novel){

        return HomeExposure.builder()
                .novel(novel)
                .build();
    }
}
