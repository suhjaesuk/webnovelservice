package com.numble.webnovelservice.episode.service

import com.numble.webnovelservice.episode.dto.request.EpisodeRegisterRequest
import com.numble.webnovelservice.episode.dto.request.EpisodeUpdateRequest
import com.numble.webnovelservice.episode.entity.Episode
import com.numble.webnovelservice.episode.repository.EpisodeRepository
import com.numble.webnovelservice.novel.entity.Novel
import com.numble.webnovelservice.novel.repository.NovelRepository
import spock.lang.Specification

class EpisodeServiceTest extends Specification{

    def "에피소드 등록 시 정상 케이스"(){

        given:
        def episodeRepository = Mock(EpisodeRepository.class)
        def novelRepository = Mock(NovelRepository.class)
        def episodeService = new EpisodeService(episodeRepository, novelRepository)

        def title = "testTitle"
        def content = "testContent"
        def totalPageCount = 0
        def neededTicketCount = 0
        def fileSize = 0.0

        EpisodeRegisterRequest request = new EpisodeRegisterRequest()
        request.setTitleForTest(title)
        request.setContentForTest(content)
        request.setTotalPageCountForTest(totalPageCount)
        request.setNeededTicketCountForTest(neededTicketCount)
        request.setFileSizeForTest(fileSize)

        def novelId = 1L

        Novel novel = Novel.builder()
                        .id(novelId)
                        .updatedAt(null)
                        .build()

        Episode episode = request.toEpisode(novel)

        //stub
        novelRepository.findById(novelId) >> Optional.of(novel)
        episodeRepository.save(episode) >> episode

        when:
        episodeService.registerEpisode(novelId, request)

        then:
        novel.getUpdatedAt() != null
        1* episodeRepository.save(_)
    }

    def "에피소드 등록 시 소설을 못찾을 경우 예외를 반환한다"(){

        given:
        def episodeRepository = Mock(EpisodeRepository.class)
        def novelRepository = Mock(NovelRepository.class)
        def episodeService = new EpisodeService(episodeRepository, novelRepository)

        def title = "testTitle"
        def content = "testContent"
        def totalPageCount = 0
        def neededTicketCount = 0
        def fileSize = 0.0

        EpisodeRegisterRequest request = new EpisodeRegisterRequest()
        request.setTitleForTest(title)
        request.setContentForTest(content)
        request.setTotalPageCountForTest(totalPageCount)
        request.setNeededTicketCountForTest(neededTicketCount)
        request.setFileSizeForTest(fileSize)

        def novelId = 1L

        Novel novel = Novel.builder()
                .id(novelId)
                .updatedAt(null)
                .build()

        Episode episode = request.toEpisode(novel)

        //stub
        novelRepository.findById(novelId) >> null

        when:
        episodeService.registerEpisode(novelId, request)

        then:
        thrown(NullPointerException)
    }

    def "에피소드 수정 시 정상 케이스"(){

        given:
        def episodeRepository = Mock(EpisodeRepository.class)
        def novelRepository = Mock(NovelRepository.class)
        def episodeService = new EpisodeService(episodeRepository, novelRepository)

        def episodeId = 1L
        def title = "testTitle"
        def content = "testContent"
        def totalPageCount = 0
        def neededTicketCount = 0
        def fileSize = 0.0

        EpisodeUpdateRequest request = new EpisodeUpdateRequest()
        request.setTitleForTest(title)
        request.setContentForTest(content)
        request.setTotalPageCountForTest(totalPageCount)
        request.setNeededTicketCountForTest(neededTicketCount)
        request.setFileSizeForTest(fileSize)

        Episode episode = Episode.builder()
                            .id(episodeId)
                            .title(title)
                            .content(content)
                            .totalPageCount(totalPageCount)
                            .neededTicketCount(0)
                            .fileSize(0)
                            .build()

        def novelId = 1L

        Novel novel = Novel.builder()
                .id(novelId)
                .build()

        //stub
        novelRepository.findById(novelId) >> Optional.of(novel)
        episodeRepository.findById(episodeId) >> Optional.of(episode)

        when:
        episodeService.updateEpisode(novelId, episodeId, request)
        def findEpisodeOptional = episodeRepository.findById(episode.getId())
        def findEpisode = findEpisodeOptional.get()

        then:
        findEpisode.getId() == episodeId
        findEpisode.getTitle() == title
        findEpisode.getContent() == content
        findEpisode.getTotalPageCount() == totalPageCount
        findEpisode.getNeededTicketCount() == neededTicketCount
        findEpisode.getFileSize() == fileSize
    }

    def "에피소드 수정 시 소설을 못찾을 경우 예외를 반환한다"(){

        given:
        def episodeRepository = Mock(EpisodeRepository.class)
        def novelRepository = Mock(NovelRepository.class)
        def episodeService = new EpisodeService(episodeRepository, novelRepository)

        def episodeId = 1L
        def title = "testTitle"
        def content = "testContent"
        def totalPageCount = 0
        def neededTicketCount = 0
        def fileSize = 0.0

        EpisodeUpdateRequest request = new EpisodeUpdateRequest()
        request.setTitleForTest(title)
        request.setContentForTest(content)
        request.setTotalPageCountForTest(totalPageCount)
        request.setNeededTicketCountForTest(neededTicketCount)
        request.setFileSizeForTest(fileSize)

        def novelId = 1L

        //stub
        novelRepository.findById(novelId) >> null

        when:
        episodeService.updateEpisode(novelId, episodeId, request)

        then:
        thrown(NullPointerException)
    }

    def "에피소드 수정 시 에피소드를 못찾을 경우 예외를 반환한다"(){

        given:
        def episodeRepository = Mock(EpisodeRepository.class)
        def novelRepository = Mock(NovelRepository.class)
        def episodeService = new EpisodeService(episodeRepository, novelRepository)

        def episodeId = 1L
        def title = "testTitle"
        def content = "testContent"
        def totalPageCount = 0
        def neededTicketCount = 0
        def fileSize = 0.0

        EpisodeUpdateRequest request = new EpisodeUpdateRequest()
        request.setTitleForTest(title)
        request.setContentForTest(content)
        request.setTotalPageCountForTest(totalPageCount)
        request.setNeededTicketCountForTest(neededTicketCount)
        request.setFileSizeForTest(fileSize)

        def novelId = 1L

        Novel novel = Novel.builder()
                .id(novelId)
                .build()

        //stub
        novelRepository.findById(novelId) >> Optional.of(novel)
        episodeRepository.findById(episodeId) >> null

        when:
        episodeService.updateEpisode(novelId, episodeId, request)

        then:
        thrown(NullPointerException)
    }

    def "에피소드 삭제 시 정상 케이스"(){

        given:
        def episodeRepository = Mock(EpisodeRepository.class)
        def novelRepository = Mock(NovelRepository.class)
        def episodeService = new EpisodeService(episodeRepository, novelRepository)

        def episodeId = 1L

        Episode episode = Episode.builder()
                .id(episodeId)
                .build()

        def novelId = 1L

        Novel novel = Novel.builder()
                .id(novelId)
                .build()

        //stub
        novelRepository.findById(novelId) >> Optional.of(novel)
        episodeRepository.findById(episodeId) >> Optional.of(episode)

        when:
        episodeService.deleteEpisode(novelId, episodeId)

        then:
        1* episodeRepository.delete(_)
    }

    def "에피소드 삭제 시 소설을 못찾을 경우 예외를 반환한다"(){

        given:
        def episodeRepository = Mock(EpisodeRepository.class)
        def novelRepository = Mock(NovelRepository.class)
        def episodeService = new EpisodeService(episodeRepository, novelRepository)

        def episodeId = 1L

        def novelId = 1L


        //stub
        novelRepository.findById(novelId) >> null

        when:
        episodeService.deleteEpisode(novelId, episodeId)

        then:
        thrown(NullPointerException)
    }

    def "에피소드 삭제 시 에피소드를 못찾을 경우 예외를 반환한다"(){

        given:
        def episodeRepository = Mock(EpisodeRepository.class)
        def novelRepository = Mock(NovelRepository.class)
        def episodeService = new EpisodeService(episodeRepository, novelRepository)

        def episodeId = 1L

        def novelId = 1L

        Novel novel = Novel.builder()
                .id(novelId)
                .build()

        //stub
        novelRepository.findById(novelId) >> Optional.of(novel)
        episodeRepository.findById(episodeId) >> null

        when:
        episodeService.deleteEpisode(novelId, episodeId)

        then:
        thrown(NullPointerException)
    }
}
