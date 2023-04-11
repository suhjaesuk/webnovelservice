package com.numble.webnovelservice.member.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.numble.webnovelservice.member.dto.request.MemberLoginRequest
import com.numble.webnovelservice.member.dto.request.MemberSignUpRequest
import com.numble.webnovelservice.member.dto.request.MemberUpdateNicknameRequest
import com.numble.webnovelservice.member.dto.request.MemberUpdateProfileImageRequest
import com.numble.webnovelservice.member.entity.Member
import com.numble.webnovelservice.util.security.UserDetailsImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

/**
 * 테스트 시 DB 초기화가 필요합니다.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MemberControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc

    static ObjectMapper objectMapper;

    static HttpHeaders httpHeaders;

    def "회원가입 정상 케이스"() {
        objectMapper = new ObjectMapper()
        httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)

        def username = "testUsername"
        def nickname = "testNickname"
        def password = "testPassword"

        def request = new MemberSignUpRequest()
        request.setUsernameForTest(username)
        request.setNicknameForTest(nickname)
        request.setPasswordForTest(password)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/sign-up")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(request)))

        then:
        response.andExpect(MockMvcResultMatchers.status().isCreated())
        response.andExpect(MockMvcResultMatchers.jsonPath('$.message').value("회원가입 성공"))
    }

    def "로그인 정상 케이스"() {
        objectMapper = new ObjectMapper()
        httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)

        def username = "testUsername"
        def password = "testPassword"

        def request = new MemberLoginRequest()
        request.setUsernameForTest(username)
        request.setPasswordForTest(password)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(request)))

        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())
        response.andExpect(MockMvcResultMatchers.jsonPath('$.message').value("로그인 성공"))
    }

    @WithMockUser
    def "닉네임 변경 정상 케이스"() {

        given:
        objectMapper = new ObjectMapper()
        httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)

        def username = "testUsername"
        def nickname = "changeTestNickname"

        def request = new MemberUpdateNicknameRequest()
        request.setNicknameForTest(nickname)

        def member = Member.builder()
                        .id(1L)
                        .username(username)
                        .nickname(nickname)
                        .build()

        def userDetails = new UserDetailsImpl(member, username)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.put("/api/members/nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(SecurityMockMvcRequestPostProcessors.user(userDetails)))

        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())
        response.andExpect(MockMvcResultMatchers.jsonPath('$.message').value("닉네임 변경 성공"))
    }

    @WithMockUser
    def "프로필 이미지 정상 케이스"() {

        given:
        objectMapper = new ObjectMapper()
        httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)
        def username = "testUsername"
        def profileImage = "testProfileImage"

        def request = new MemberUpdateProfileImageRequest()
        request.setProfileImageForTest(profileImage)

        def member = Member.builder()
                .id(1L)
                .username(username)
                .profileImage(profileImage)
                .build()

        def userDetails = new UserDetailsImpl(member, username)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.put("/api/members/profile-image")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(SecurityMockMvcRequestPostProcessors.user(userDetails)))

        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())
        response.andExpect(MockMvcResultMatchers.jsonPath('$.message').value("프로필 이미지 변경 성공"))
    }
}
