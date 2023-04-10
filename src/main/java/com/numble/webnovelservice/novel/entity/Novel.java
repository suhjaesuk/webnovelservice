package com.numble.webnovelservice.novel.entity;

import com.numble.webnovelservice.util.time.Timestamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Novel extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="novel_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(nullable = false)
    private String coverImage;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SerializedStatus serializedStatus;

    @Column(nullable = false)
    private Integer totalViewCount;

    @Column
    private LocalDateTime updatedAt;

    @Builder
    public Novel(Long id, String title, String description, String author, Genre genre, String coverImage, SerializedStatus serializedStatus, Integer totalViewCount, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.genre = genre;
        this.coverImage = coverImage;
        this.serializedStatus = serializedStatus;
        this.totalViewCount = totalViewCount;
        this.updatedAt = updatedAt;
    }
}