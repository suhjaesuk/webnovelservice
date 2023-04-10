package com.numble.webnovelservice.novel.repository


import com.numble.webnovelservice.novel.entity.Genre
import com.numble.webnovelservice.novel.entity.Novel
import com.numble.webnovelservice.novel.entity.SerializedStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class NovelRepositoryTest extends Specification{

    @Autowired
    NovelRepository novelRepository;

    def "Novel 생성 시 정상케이스"() {

        given:
        def title = "testTitle"
        def description = "testDescription"
        def author = "testAuthor"
        def genre = Genre.COMEDY
        def serializedStatus = SerializedStatus.SERIALIZING
        def coverImage = "testCoverImage"
        def totalViewCount = 0
        def likeCount = 0
        def updatedAt = LocalDateTime.now()

        Novel expectedNovel = Novel.builder()
                .title(title)
                .description(description)
                .author(author)
                .genre(genre)
                .serializedStatus(serializedStatus)
                .coverImage(coverImage)
                .totalViewCount(totalViewCount)
                .likeCount(likeCount)
                .updatedAt(updatedAt)
                .build()

        when:
        Novel novel = novelRepository.save(expectedNovel)

        then:
        novel.getTitle() == title
        novel.getDescription() == description
        novel.getAuthor() == author
        novel.getGenre() == genre
        novel.getSerializedStatus() == serializedStatus
        novel.getCoverImage() == coverImage
        novel.getTotalViewCount() == totalViewCount
        novel.getLikeCount() == likeCount
        novel.getUpdatedAt() == updatedAt
    }

    def "Novel 생성 시 title이 null일 경우 예외가 발생한다"() {

        given:
        def title = null
        def description = "testDescription"
        def author = "testAuthor"
        def genre = Genre.COMEDY
        def serializedStatus = SerializedStatus.SERIALIZING
        def coverImage = "testCoverImage"
        def totalViewCount = 0
        def likeCount = 0
        def updatedAt = LocalDateTime.now()

        Novel expectedNovel = Novel.builder()
                .title(title)
                .description(description)
                .author(author)
                .genre(genre)
                .serializedStatus(serializedStatus)
                .coverImage(coverImage)
                .totalViewCount(totalViewCount)
                .likeCount(likeCount)
                .updatedAt(updatedAt)
                .build()

        when:
        novelRepository.save(expectedNovel)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "Novel 생성 시 description이 null일 경우 예외가 발생한다"() {

        given:
        def title = "testTitle"
        def description = null
        def author = "testAuthor"
        def genre = Genre.COMEDY
        def serializedStatus = SerializedStatus.SERIALIZING
        def coverImage = "testCoverImage"
        def totalViewCount = 0
        def likeCount = 0
        def updatedAt = LocalDateTime.now()

        Novel expectedNovel = Novel.builder()
                .title(title)
                .description(description)
                .author(author)
                .genre(genre)
                .serializedStatus(serializedStatus)
                .coverImage(coverImage)
                .totalViewCount(totalViewCount)
                .likeCount(likeCount)
                .updatedAt(updatedAt)
                .build()

        when:
        novelRepository.save(expectedNovel)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "Novel 생성 시 author가 null일 경우 예외가 발생한다"() {

        given:
        def title = "testTitle"
        def description = "testDescription"
        def author = null
        def genre = Genre.COMEDY
        def serializedStatus = SerializedStatus.SERIALIZING
        def coverImage = "testCoverImage"
        def totalViewCount = 0
        def likeCount = 0
        def updatedAt = LocalDateTime.now()

        Novel expectedNovel = Novel.builder()
                .title(title)
                .description(description)
                .author(author)
                .genre(genre)
                .serializedStatus(serializedStatus)
                .coverImage(coverImage)
                .totalViewCount(totalViewCount)
                .likeCount(likeCount)
                .updatedAt(updatedAt)
                .build()

        when:
        novelRepository.save(expectedNovel)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "Novel 생성 시 genre가 null일 경우 예외가 발생한다"() {

        given:
        def title = "testTitle"
        def description = "testDescription"
        def author = "testAuthor"
        def genre = null
        def serializedStatus = SerializedStatus.SERIALIZING
        def coverImage = "testCoverImage"
        def totalViewCount = 0
        def likeCount = 0
        def updatedAt = LocalDateTime.now()

        Novel expectedNovel = Novel.builder()
                .title(title)
                .description(description)
                .author(author)
                .genre(genre)
                .serializedStatus(serializedStatus)
                .coverImage(coverImage)
                .totalViewCount(totalViewCount)
                .likeCount(likeCount)
                .updatedAt(updatedAt)
                .build()

        when:
        novelRepository.save(expectedNovel)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "Novel 생성 시 serializedStatus가 null일 경우 예외가 발생한다"() {

        given:
        def title = "testTitle"
        def description = "testDescription"
        def author = "testAuthor"
        def genre = Genre.COMEDY
        def serializedStatus = null
        def coverImage = "testCoverImage"
        def totalViewCount = 0
        def likeCount = 0
        def updatedAt = LocalDateTime.now()

        Novel expectedNovel = Novel.builder()
                .title(title)
                .description(description)
                .author(author)
                .genre(genre)
                .serializedStatus(serializedStatus)
                .coverImage(coverImage)
                .totalViewCount(totalViewCount)
                .likeCount(likeCount)
                .updatedAt(updatedAt)
                .build()

        when:
        novelRepository.save(expectedNovel)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "Novel 생성 시 coverImage가 null일 경우 예외가 발생한다"() {

        given:
        def title = "testTitle"
        def description = "testDescription"
        def author = "testAuthor"
        def genre = Genre.COMEDY
        def serializedStatus = SerializedStatus.SERIALIZING
        def coverImage = null
        def totalViewCount = 0
        def likeCount = 0
        def updatedAt = LocalDateTime.now()

        Novel expectedNovel = Novel.builder()
                .title(title)
                .description(description)
                .author(author)
                .genre(genre)
                .serializedStatus(serializedStatus)
                .coverImage(coverImage)
                .totalViewCount(totalViewCount)
                .likeCount(likeCount)
                .updatedAt(updatedAt)
                .build()

        when:
        novelRepository.save(expectedNovel)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "Novel 생성 시 totalViewCount가 null일 경우 예외가 발생한다"() {

        given:
        def title = "testTitle"
        def description = "testDescription"
        def author = "testAuthor"
        def genre = Genre.COMEDY
        def serializedStatus = SerializedStatus.SERIALIZING
        def coverImage = "testCoverImage"
        def totalViewCount = null
        def likeCount = 0
        def updatedAt = LocalDateTime.now()

        Novel expectedNovel = Novel.builder()
                .title(title)
                .description(description)
                .author(author)
                .genre(genre)
                .serializedStatus(serializedStatus)
                .coverImage(coverImage)
                .totalViewCount(totalViewCount)
                .likeCount(likeCount)
                .updatedAt(updatedAt)
                .build()

        when:
        novelRepository.save(expectedNovel)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "Novel 생성 시 likeCount가 null일 경우 예외가 발생한다"() {

        given:
        def title = "testTitle"
        def description = "testDescription"
        def author = "testAuthor"
        def genre = Genre.COMEDY
        def serializedStatus = SerializedStatus.SERIALIZING
        def coverImage = "testCoverImage"
        def totalViewCount = 0
        def likeCount = null
        def updatedAt = LocalDateTime.now()

        Novel expectedNovel = Novel.builder()
                .title(title)
                .description(description)
                .author(author)
                .genre(genre)
                .serializedStatus(serializedStatus)
                .coverImage(coverImage)
                .totalViewCount(totalViewCount)
                .likeCount(likeCount)
                .updatedAt(updatedAt)
                .build()

        when:
        novelRepository.save(expectedNovel)

        then:
        thrown(DataIntegrityViolationException)
    }
}
