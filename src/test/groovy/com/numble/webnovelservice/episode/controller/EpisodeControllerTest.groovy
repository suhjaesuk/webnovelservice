package com.numble.webnovelservice.episode.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.numble.webnovelservice.episode.dto.request.EpisodeRegisterRequest
import com.numble.webnovelservice.episode.dto.request.EpisodeUpdateRequest
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
class EpisodeControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc

    static ObjectMapper objectMapper

    static HttpHeaders httpHeaders

    @WithMockUser
    def "에피소드 등록 정상 케이스"(){

        objectMapper = new ObjectMapper()
        httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)

        def title = "testTitle"
        def content = "testContent"
        def totalPageCount = 0
        def neededTicketCount = 0
        Float fileSize = 0.0

        def request = new EpisodeRegisterRequest()
        request.setTitleForTest(title)
        request.setContentForTest(content)
        request.setTotalPageCountForTest(totalPageCount)
        request.setNeededTicketCountForTest(neededTicketCount)
        request.setFileSizeForTest(fileSize)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/api/novels/2/episodes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

        then:
        response.andExpect(MockMvcResultMatchers.status().isCreated())
        response.andExpect(MockMvcResultMatchers.jsonPath('$.message').value("에피소드 등록 성공"))
    }

    @WithMockUser
    def "에피소드 수정 정상 케이스"(){

        objectMapper = new ObjectMapper()
        httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)

        def title = "testTitle"
        def content = "testContent"
        def totalPageCount = 0
        def neededTicketCount = 0
        Float fileSize = 0.0

        def request = new EpisodeUpdateRequest()
        request.setTitleForTest(title)
        request.setContentForTest(content)
        request.setTotalPageCountForTest(totalPageCount)
        request.setNeededTicketCountForTest(neededTicketCount)
        request.setFileSizeForTest(fileSize)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.put("/api/novels/2/episodes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())
        response.andExpect(MockMvcResultMatchers.jsonPath('$.message').value("에피소드 수정 성공"))
    }

    @WithMockUser
    def "에피소드 삭제 정상 케이스"(){

        objectMapper = new ObjectMapper()
        httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/novels/2/episodes/1")
                .contentType(MediaType.APPLICATION_JSON))

        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())
        response.andExpect(MockMvcResultMatchers.jsonPath('$.message').value("에피소드 삭제 성공"))
    }
}