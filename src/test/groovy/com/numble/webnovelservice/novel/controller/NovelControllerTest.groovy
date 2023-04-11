package com.numble.webnovelservice.novel.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.numble.webnovelservice.novel.dto.request.NovelRegisterRequest
import com.numble.webnovelservice.novel.dto.request.NovelUpdateInfoRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class NovelControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc

    static ObjectMapper objectMapper;

    static HttpHeaders httpHeaders;

    @WithMockUser
    def "소설 등록 정상 케이스"() {
        objectMapper = new ObjectMapper()
        httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)

        def title = "testTitle"
        def description = "testDescription"
        def author = "testAuthor"
        def genre = "코미디"
        def serializedStatus ="연재 중"
        def coverImage = "testCoverImage"

        def request = new NovelRegisterRequest()
        request.setTitleForTest(title)
        request.setDescriptionForTest(description)
        request.setAuthorForTest(author)
        request.setGenreForTest(genre)
        request.setSerializedStatusForTest(serializedStatus)
        request.setCoverImageForTest(coverImage)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/api/novels")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(request)))

        then:
        response.andExpect(MockMvcResultMatchers.status().isCreated())
        response.andExpect(MockMvcResultMatchers.jsonPath('$.message').value("소설 등록 성공"))
    }

    @WithMockUser
    def "소설 정보 수정 정상 케이스"() {
        objectMapper = new ObjectMapper()
        httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)

        def description = "testDescription"
        def serializedStatus ="연재 중"
        def coverImage = "testCoverImage"

        def request = new NovelUpdateInfoRequest()
        request.setDescriptionForTest(description)
        request.setSerializedStatusForTest(serializedStatus)
        request.setCoverImageForTest(coverImage)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.put("/api/novels/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(request)))

        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())
        response.andExpect(MockMvcResultMatchers.jsonPath('$.message').value("소설 정보 수정 성공"))
    }

    @WithMockUser
    def "소설 삭제 정상 케이스"() {
        objectMapper = new ObjectMapper()
        httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/novels/1")
                                        .contentType(MediaType.APPLICATION_JSON))

        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())
        response.andExpect(MockMvcResultMatchers.jsonPath('$.message').value("소설 삭제 성공"))
    }

    @WithMockUser
    def "소설 전체 조회 정상 케이스"() {
        objectMapper = new ObjectMapper()
        httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/api/novels")
                                        .contentType(MediaType.APPLICATION_JSON))

        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())
        response.andExpect(MockMvcResultMatchers.jsonPath('$.message').value("소설 전체 조회 성공"))
        response.andExpect(MockMvcResultMatchers.jsonPath('$.data.novels').isArray())
    }

    @WithMockUser
    def "장르별 소설 조회 정상 케이스"() {
        objectMapper = new ObjectMapper()
        httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/api/novels/코미디")
                                        .contentType(MediaType.APPLICATION_JSON))

        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())
        response.andExpect(MockMvcResultMatchers.jsonPath('$.message').value("장르별 소설 조회 성공"))
        response.andExpect(MockMvcResultMatchers.jsonPath('$.data.novels').isArray())
    }

    @WithMockUser
    def "소설 검색 정상 케이스"() {
        objectMapper = new ObjectMapper()
        httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/api/novels/search/titleOrAuthor")
                                        .contentType(MediaType.APPLICATION_JSON))

        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())
        response.andExpect(MockMvcResultMatchers.jsonPath('$.message').value("소설 검색 성공"))
        response.andExpect(MockMvcResultMatchers.jsonPath('$.data.novels').isArray())
    }

    @WithMockUser
    def "최신 업데이트 소설 조회 정상 케이스"() {
        objectMapper = new ObjectMapper()
        httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/api/novels/latest")
                                        .contentType(MediaType.APPLICATION_JSON))

        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())
        response.andExpect(MockMvcResultMatchers.jsonPath('$.message').value("최신 업데이트 소설 조회 성공"))
        response.andExpect(MockMvcResultMatchers.jsonPath('$.data.novels').isArray())
    }
}