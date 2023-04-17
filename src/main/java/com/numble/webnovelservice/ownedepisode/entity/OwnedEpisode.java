package com.numble.webnovelservice.ownedepisode.entity;

import com.numble.webnovelservice.episode.entity.Episode;
import com.numble.webnovelservice.member.entity.Member;
import com.numble.webnovelservice.util.time.Timestamped;
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
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnedEpisode extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="owned_episode_id")
    private Long id;

    @Column(nullable = true)
    private Integer currentReadingPage;

    @Column(nullable = false)
    private Boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="episode_id")
    private Episode episode;

    @Builder
    public OwnedEpisode(Long id, Integer currentReadingPage, Boolean isRead, Member member, Episode episode) {
        this.id = id;
        this.currentReadingPage = currentReadingPage;
        this.isRead = isRead;
        this.member = member;
        this.episode = episode;
    }

    public static OwnedEpisode createOwnedEpisode(Member member, Episode episode) {
        return OwnedEpisode.builder()
                .isRead(false)
                .currentReadingPage(null)
                .episode(episode)
                .member(member)
                .build();
    }

    public void markAsRead() {
        this.isRead = true;
        this.currentReadingPage = Optional.ofNullable(currentReadingPage)
                                    .orElse(1);
    }

    public void readNextPage() {
        this.currentReadingPage++;

    }

    public void readPreviousPage() {
        this.currentReadingPage--;
    }
}
