package com.numble.webnovelservice.novel.service

import com.numble.webnovelservice.common.exception.WebNovelServiceException
import com.numble.webnovelservice.novel.dto.request.NovelRegisterRequest
import com.numble.webnovelservice.novel.dto.request.NovelUpdateInfoRequest
import com.numble.webnovelservice.novel.dto.response.NovelInfoResponseList
import com.numble.webnovelservice.novel.entity.Genre
import com.numble.webnovelservice.novel.entity.Novel
import com.numble.webnovelservice.novel.entity.SerializedStatus
import com.numble.webnovelservice.novel.repository.NovelRepository
import spock.lang.Specification

import java.time.LocalDateTime

class NovelServiceTest  extends Specification{

    def "소설 등록 시 정상 케이스"(){

        given:
        def novelRepository = Mock(NovelRepository.class)
        def novelService = new NovelService(novelRepository)

        def title = "testTitle"
        def description = "testDescription"
        def author = "testAuthor"
        def genre = "코미디"
        def serializedStatus = "연재 중"
        def coverImage = "testCoverImage"

        NovelRegisterRequest request = new NovelRegisterRequest()
        request.setTitleForTest(title)
        request.setDescriptionForTest(description)
        request.setAuthorForTest(author)
        request.setGenreForTest(genre)
        request.setSerializedStatusForTest(serializedStatus)
        request.setCoverImageForTest(coverImage)

        Novel novel = request.toNovel()

        //stub
        novelRepository.save(novel) >> novel

        when:
        novelService.registerNovel(request)

        then:
        1* novelRepository.save(_)
    }

    def "소설 등록 시 유효한 장르가 아닐 경우 예외를 반환한다"(){

        given:
        def novelRepository = Mock(NovelRepository.class)
        def novelService = new NovelService(novelRepository)

        def title = "testTitle"
        def description = "testDescription"
        def author = "testAuthor"
        def genre = "유효하지 않은 장르"
        def serializedStatus = "연재 중"
        def coverImage = "testCoverImage"

        NovelRegisterRequest request = new NovelRegisterRequest()
        request.setTitleForTest(title)
        request.setDescriptionForTest(description)
        request.setAuthorForTest(author)
        request.setGenreForTest(genre)
        request.setSerializedStatusForTest(serializedStatus)
        request.setCoverImageForTest(coverImage)

        when:
        novelService.registerNovel(request)

        then:
        thrown(WebNovelServiceException)
    }

    def "소설 등록 시 유효한 연재상태가 아닐 경우 예외를 반환한다"(){

        given:
        def novelRepository = Mock(NovelRepository.class)
        def novelService = new NovelService(novelRepository)

        def title = "testTitle"
        def description = "testDescription"
        def author = "testAuthor"
        def genre = "코미디"
        def serializedStatus = "유효하지 않은 연재상태"
        def coverImage = "testCoverImage"

        NovelRegisterRequest request = new NovelRegisterRequest()
        request.setTitleForTest(title)
        request.setDescriptionForTest(description)
        request.setAuthorForTest(author)
        request.setGenreForTest(genre)
        request.setSerializedStatusForTest(serializedStatus)
        request.setCoverImageForTest(coverImage)

        when:
        novelService.registerNovel(request)

        then:
        thrown(WebNovelServiceException)
    }

    def "소설 정보 업데이트 시 정상 케이스"(){

        given:
        def novelRepository = Mock(NovelRepository.class)
        def novelService = new NovelService(novelRepository)

        def novelId = 1L
        def description = "testDescription"
        def coverImage = "testCoverImage"
        def serializedStatus = SerializedStatus.SERIALIZING

        NovelUpdateInfoRequest request = new NovelUpdateInfoRequest()
        request.setDescriptionForTest(description)
        request.setSerializedStatusForTest(serializedStatus.getKoreanName())
        request.setCoverImageForTest(coverImage)

        Novel novel = Novel.builder()
                        .id(novelId)
                        .description(description)
                        .coverImage(coverImage)
                        .serializedStatus(serializedStatus)
                        .build()

        //stub
        novelRepository.findById(novelId) >> Optional.of(novel)

        when:
        novelService.updateNovelInfo(novelId,request)
        def findNovelOptional = novelRepository.findById(novel.getId())
        def findNovel = findNovelOptional.get()

        then:
        findNovel.getDescription() == description
        findNovel.getCoverImage() == coverImage
        findNovel.getSerializedStatus() == serializedStatus
    }

    def "소설 정보 업데이트 시 유효한 연재상태가 아닐 경우 예외를 반환한다"(){

        given:
        def novelRepository = Mock(NovelRepository.class)
        def novelService = new NovelService(novelRepository)

        def novelId = 1L
        def description = "testDescription"
        def coverImage = "testCoverImage"
        def serializedStatus = "유효하지 않은 status"

        NovelUpdateInfoRequest request = new NovelUpdateInfoRequest()
        request.setDescriptionForTest(description)
        request.setSerializedStatusForTest(serializedStatus)
        request.setCoverImageForTest(coverImage)

        Novel novel = Novel.builder()
                .id(novelId)
                .description(description)
                .coverImage(coverImage)
                .serializedStatus(null)
                .build()

        //stub
        novelRepository.findById(novelId) >> Optional.of(novel)

        when:
        novelService.updateNovelInfo(novelId,request)

        then:
        thrown(WebNovelServiceException)
    }

    def "소설 정보 업데이트 시 소설을 못찾을 경우 예외를 반환한다."(){

        given:
        def novelRepository = Mock(NovelRepository.class)
        def novelService = new NovelService(novelRepository)

        def novelId = null
        def description = "testDescription"
        def coverImage = "testCoverImage"
        def serializedStatus = SerializedStatus.SERIALIZING

        NovelUpdateInfoRequest request = new NovelUpdateInfoRequest()
        request.setDescriptionForTest(description)
        request.setSerializedStatusForTest(serializedStatus.getKoreanName())
        request.setCoverImageForTest(coverImage)

        Novel novel = Novel.builder()
                .id(novelId)
                .description(description)
                .coverImage(coverImage)
                .serializedStatus(serializedStatus)
                .build()

        //stub
        novelRepository.findById(novelId) >> null

        when:
        novelService.updateNovelInfo(novelId,request)


        then:
        thrown(NullPointerException)
    }

    def "소설 삭제 시 정상 케이스"(){

        given:
        def novelRepository = Mock(NovelRepository.class)
        def novelService = new NovelService(novelRepository)

        def novelId = 1L

        Novel novel = Novel.builder()
                .id(novelId)
                .build()

        //stub
        novelRepository.findById(novelId) >> Optional.of(novel)

        when:
        novelService.deleteNovel(novelId)

        then:
        1* novelRepository.delete(_)
    }

    def "소설 삭제 시 소설을 못찾을 경우 예외를 반환한다"(){

        given:
        def novelRepository = Mock(NovelRepository.class)
        def novelService = new NovelService(novelRepository)

        def novelId = null

        Novel novel = Novel.builder()
                .id(novelId)
                .build()

        //stub
        novelRepository.findById(novelId) >> null

        when:
        novelService.deleteNovel(novelId)

        then:
        thrown(NullPointerException)
    }

    def "소설 전체 조회 정상 케이스"(){

        given:
        def novelRepository = Mock(NovelRepository.class)
        def novelService = new NovelService(novelRepository)

        List<Novel> novels = new ArrayList<>();
        Novel novel1 = Novel.builder()
                .id(1L)
                .title("testTitle1")
                .author("testAuthor1")
                .genre(Genre.COMEDY)
                .coverImage("testCoverImage1")
                .serializedStatus(SerializedStatus.SERIALIZING)
                .likeCount(0)
                .totalViewCount(0)
                .updatedAt(LocalDateTime.now())
                .build()

        Novel novel2 = Novel.builder()
                .id(2L)
                .title("testTitle2")
                .author("testAuthor2")
                .genre(Genre.SCIENCE_FICTION)
                .coverImage("testCoverImage2")
                .serializedStatus(SerializedStatus.FINISHED)
                .likeCount(10)
                .totalViewCount(10)
                .updatedAt(LocalDateTime.now())
                .build()

        novels.add(novel1)
        novels.add(novel2)

        //stub
        novelRepository.findAll() >> novels

        when:
        NovelInfoResponseList response = novelService.retrieveAllNovels()
        def novel = response.novels[0]

        then:
        response.novels.size() == 2
        novel.novelId == 1L
        novel.title == "testTitle1"
        novel.author == "testAuthor1"
        novel.genre == "코미디"
        novel.coverImage == "testCoverImage1"
        novel.serializedStatus == "연재 중"
        novel.likeCount == 0
        novel.totalViewCount == 0
    }

    def "장르별 소설 조회 정상 케이스"(){

        given:
        def novelRepository = Mock(NovelRepository.class)
        def novelService = new NovelService(novelRepository)

        List<Novel> novels = new ArrayList<>();
        Novel novel1 = Novel.builder()
                .id(1L)
                .title("testTitle1")
                .author("testAuthor1")
                .genre(Genre.COMEDY)
                .coverImage("testCoverImage1")
                .serializedStatus(SerializedStatus.SERIALIZING)
                .likeCount(0)
                .totalViewCount(0)
                .updatedAt(LocalDateTime.now())
                .build()

        Novel novel2 = Novel.builder()
                .id(2L)
                .title("testTitle2")
                .author("testAuthor2")
                .genre(Genre.COMEDY)
                .coverImage("testCoverImage2")
                .serializedStatus(SerializedStatus.FINISHED)
                .likeCount(10)
                .totalViewCount(10)
                .updatedAt(LocalDateTime.now())
                .build()

        novels.add(novel1)
        novels.add(novel2)

        //stub
        Genre.fromKoreanName("코미디") >> Genre.COMEDY
        novelRepository.findByGenre(Genre.COMEDY) >> novels

        when:
        NovelInfoResponseList response = novelService.retrieveNovelsByGenre("코미디")
        def novel = response.novels[0]

        then:
        response.novels.size() == 2
        novel.novelId == 1L
        novel.title == "testTitle1"
        novel.author == "testAuthor1"
        novel.genre == "코미디"
        novel.coverImage == "testCoverImage1"
        novel.serializedStatus == "연재 중"
        novel.likeCount == 0
        novel.totalViewCount == 0
    }

    def "소설 검색 시 정상 케이스"(){

        given:
        def novelRepository = Mock(NovelRepository.class)
        def novelService = new NovelService(novelRepository)

        List<Novel> novels = new ArrayList<>();
        Novel novel1 = Novel.builder()
                .id(1L)
                .title("testTitle1")
                .author("testAuthor1")
                .genre(Genre.COMEDY)
                .coverImage("testCoverImage1")
                .serializedStatus(SerializedStatus.SERIALIZING)
                .likeCount(0)
                .totalViewCount(0)
                .updatedAt(LocalDateTime.now())
                .build()

        Novel novel2 = Novel.builder()
                .id(2L)
                .title("testTitle2")
                .author("testAuthor2")
                .genre(Genre.COMEDY)
                .coverImage("testCoverImage2")
                .serializedStatus(SerializedStatus.FINISHED)
                .likeCount(10)
                .totalViewCount(10)
                .updatedAt(LocalDateTime.now())
                .build()

        novels.add(novel1)
        novels.add(novel2)

        //stub
        novelRepository.findByTitleContainingOrAuthorContaining("testTitle", "testTitle") >> novels

        when:
        NovelInfoResponseList response = novelService.retrieveNovelsBySearch("testTitle")
        def novel = response.novels[0]

        then:
        response.novels.size() == 2
        novel.novelId == 1L
        novel.title == "testTitle1"
        novel.author == "testAuthor1"
        novel.genre == "코미디"
        novel.coverImage == "testCoverImage1"
        novel.serializedStatus == "연재 중"
        novel.likeCount == 0
        novel.totalViewCount == 0
    }

    def "최신 업데이트 소설 조회 시 정상 케이스"(){

        given:
        def novelRepository = Mock(NovelRepository.class)
        def novelService = new NovelService(novelRepository)

        List<Novel> novels = new ArrayList<>();
        Novel novel1 = Novel.builder()
                .id(1L)
                .title("testTitle1")
                .author("testAuthor1")
                .genre(Genre.COMEDY)
                .coverImage("testCoverImage1")
                .serializedStatus(SerializedStatus.SERIALIZING)
                .likeCount(0)
                .totalViewCount(0)
                .updatedAt(LocalDateTime.now())
                .build()

        Novel novel2 = Novel.builder()
                .id(2L)
                .title("testTitle2")
                .author("testAuthor2")
                .genre(Genre.COMEDY)
                .coverImage("testCoverImage2")
                .serializedStatus(SerializedStatus.FINISHED)
                .likeCount(10)
                .totalViewCount(10)
                .updatedAt(LocalDateTime.now())
                .build()

        novels.add(novel1)
        novels.add(novel2)

        //stub
        novelRepository.findByOrderByUpdatedAtDesc() >> novels

        when:
        NovelInfoResponseList response = novelService.retrieveLatestUpdateNovels()
        def novel = response.novels[0]

        then:
        response.novels.size() == 2
        novel.novelId == 1L
        novel.title == "testTitle1"
        novel.author == "testAuthor1"
        novel.genre == "코미디"
        novel.coverImage == "testCoverImage1"
        novel.serializedStatus == "연재 중"
        novel.likeCount == 0
        novel.totalViewCount == 0
    }
}
