package com.numble.webnovelservice.like.entity;

import com.numble.webnovelservice.member.entity.Member;
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
public class FavoriteNovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="favorite_novel_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="novel_id")
    private Novel novel;

    @Builder
    public FavoriteNovel(Long id, Member member, Novel novel) {
        this.id = id;
        this.member = member;
        this.novel = novel;
    }

    public static FavoriteNovel createFavoriteNovel(Member member, Novel novel) {

        return FavoriteNovel.builder()
                .member(member)
                .novel(novel)
                .build();
    }
}
