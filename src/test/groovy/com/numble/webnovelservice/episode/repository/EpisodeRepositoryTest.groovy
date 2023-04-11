package com.numble.webnovelservice.episode.repository

import com.numble.webnovelservice.episode.entity.Episode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class EpisodeRepositoryTest extends Specification{

    @Autowired
    EpisodeRepository episodeRepository;

    def "Episode 생성 시 정상케이스"() {

        given:
        def title = "testTitle"
        def content = "testContent"
        def totalPageCount = 0
        def isFree = true
        def neededTicketCount = 0
        def fileSize = 0.0
        def viewCount = 0

        Episode expectedEpisode = Episode.builder()
                .title(title)
                .content(content)
                .totalPageCount(totalPageCount)
                .isFree(isFree)
                .neededTicketCount(neededTicketCount)
                .fileSize(fileSize)
                .viewCount(viewCount)
                .build()

        when:
        Episode episode = episodeRepository.save(expectedEpisode)

        then:
        episode.getTitle() == title
        episode.getContent() == content
        episode.getTotalPageCount() == totalPageCount
        episode.isFree() == isFree
        episode.getNeededTicketCount() == neededTicketCount
        episode.getFileSize() == fileSize
        episode.getViewCount() == viewCount
    }

    def "Episode 생성 시 제목이 null일 경우 예외가 발생한다"() {

        given:
        def title = null
        def content = "testContent"
        def totalPageCount = 0
        def isFree = true
        def neededTicketCount = 0
        def fileSize = 0.0
        def viewCount = 0

        Episode expectedEpisode = Episode.builder()
                .title(title)
                .content(content)
                .totalPageCount(totalPageCount)
                .isFree(isFree)
                .neededTicketCount(neededTicketCount)
                .fileSize(fileSize)
                .viewCount(viewCount)
                .build()

        when:
        episodeRepository.save(expectedEpisode)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "Episode 생성 시 내용이 null일 경우 예외가 발생한다"() {

        given:
        def title = "testTitle"
        def content = null
        def totalPageCount = 0
        def isFree = true
        def neededTicketCount = 0
        def fileSize = 0.0
        def viewCount = 0

        Episode expectedEpisode = Episode.builder()
                .title(title)
                .content(content)
                .totalPageCount(totalPageCount)
                .isFree(isFree)
                .neededTicketCount(neededTicketCount)
                .fileSize(fileSize)
                .viewCount(viewCount)
                .build()

        when:
        episodeRepository.save(expectedEpisode)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "Episode 생성 시 총 페이지 수가 null일 경우 예외가 발생한다"() {

        given:
        def title = "testTitle"
        def content = "testContent"
        def totalPageCount = null
        def isFree = true
        def neededTicketCount = 0
        def fileSize = 0.0
        def viewCount = 0

        Episode expectedEpisode = Episode.builder()
                .title(title)
                .content(content)
                .totalPageCount(totalPageCount)
                .isFree(isFree)
                .neededTicketCount(neededTicketCount)
                .fileSize(fileSize)
                .viewCount(viewCount)
                .build()

        when:
        episodeRepository.save(expectedEpisode)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "Episode 생성 시 무료 여부가 null일 경우 예외가 발생한다"() {

        given:
        def title = "testTitle"
        def content = "testContent"
        def totalPageCount = 0
        def isFree = null
        def neededTicketCount = 0
        def fileSize = 0.0
        def viewCount = 0

        when:
        Episode expectedEpisode = Episode.builder()
                .title(title)
                .content(content)
                .totalPageCount(totalPageCount)
                .isFree(isFree)
                .neededTicketCount(neededTicketCount)
                .fileSize(fileSize)
                .viewCount(viewCount)
                .build()

        episodeRepository.save(expectedEpisode)

        then:
        thrown(NullPointerException)
    }

    def "Episode 생성 시 필요한 티켓 수가 null일 경우 예외가 발생한다"() {

        given:
        def title = "testTitle"
        def content = "testContent"
        def totalPageCount = 0
        def isFree = true
        def neededTicketCount = null
        def fileSize = 0.0
        def viewCount = 0

        Episode expectedEpisode = Episode.builder()
                .title(title)
                .content(content)
                .totalPageCount(totalPageCount)
                .isFree(isFree)
                .neededTicketCount(neededTicketCount)
                .fileSize(fileSize)
                .viewCount(viewCount)
                .build()

        when:
        episodeRepository.save(expectedEpisode)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "Episode 생성 시 파일 크기가 null일 경우 예외가 발생한다"() {

        given:
        def title = "testTitle"
        def content = "testContent"
        def totalPageCount = 0
        def isFree = true
        def neededTicketCount = 0
        def fileSize = null
        def viewCount = 0

        Episode expectedEpisode = Episode.builder()
                .title(title)
                .content(content)
                .totalPageCount(totalPageCount)
                .isFree(isFree)
                .neededTicketCount(neededTicketCount)
                .fileSize(fileSize)
                .viewCount(viewCount)
                .build()

        when:
        episodeRepository.save(expectedEpisode)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "Episode 생성 시 조회수가 null일 경우 예외가 발생한다"() {

        given:
        def title = "testTitle"
        def content = "testContent"
        def totalPageCount = 0
        def isFree = true
        def neededTicketCount = 0
        def fileSize = 0
        def viewCount = null

        Episode expectedEpisode = Episode.builder()
                .title(title)
                .content(content)
                .totalPageCount(totalPageCount)
                .isFree(isFree)
                .neededTicketCount(neededTicketCount)
                .fileSize(fileSize)
                .viewCount(viewCount)
                .build()

        when:
        episodeRepository.save(expectedEpisode)

        then:
        thrown(DataIntegrityViolationException)
    }
}
