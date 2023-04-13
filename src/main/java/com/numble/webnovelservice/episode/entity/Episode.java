package com.numble.webnovelservice.episode.entity;

import com.numble.webnovelservice.episode.dto.request.EpisodeUpdateRequest;
import com.numble.webnovelservice.novel.entity.Novel;
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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Episode extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="episode_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer totalPageCount;

    @Column(nullable = false)
    private Boolean isFree;

    @Column(nullable = false)
    private Integer neededTicketCount;

    @Column(nullable = false)
    private Float fileSize;

    @Column(nullable = false)
    private Integer viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="novel_id")
    private Novel novel;

    @Builder
    public Episode(Long id, String title, String content, Integer totalPageCount, Boolean isFree, Integer neededTicketCount, Float fileSize, Integer viewCount, Novel novel) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.totalPageCount = totalPageCount;
        this.isFree = isFree;
        this.neededTicketCount = neededTicketCount;
        this.fileSize = fileSize;
        this.viewCount = viewCount;
        this.novel = novel;
    }

    public void updateInfo(EpisodeUpdateRequest request){

        Boolean isFree = (request.getNeededTicketCount() == 0);

        this.title = request.getTitle();
        this.content = request.getContent();
        this.totalPageCount = request.getTotalPageCount();
        this.isFree = isFree;
        this.neededTicketCount = request.getNeededTicketCount();
        this.fileSize = request.getFileSize();
    }

    public void incrementViewCount(){

        this.viewCount++;
    }
}
