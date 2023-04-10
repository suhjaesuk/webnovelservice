package com.numble.webnovelservice.member.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import com.numble.webnovelservice.member.dto.request.MemberLoginRequest
import com.numble.webnovelservice.member.dto.request.MemberSignUpRequest
import com.numble.webnovelservice.member.dto.request.MemberUpdateNicknameRequest
import com.numble.webnovelservice.member.dto.request.MemberUpdateProfileImageRequest
import com.numble.webnovelservice.member.entity.Member
import com.numble.webnovelservice.util.security.UserDetailsImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MemberControllerTest extends Specification {

    @Autowired
    TestRestTemplate restTemplate;

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

        def body = objectMapper.writeValueAsString(request)

        when:
        def httpEntity = new HttpEntity(body, httpHeaders)
        def responseEntity = restTemplate.exchange("/api/auth/sign-up", HttpMethod.POST, httpEntity, String.class)

        then:
        responseEntity.statusCode == HttpStatus.CREATED
        def documentContext = JsonPath.parse(responseEntity.getBody())

        documentContext.read('$.message') == "회원가입 성공"
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

        def body = objectMapper.writeValueAsString(request)

        when:
        def httpEntity = new HttpEntity(body, httpHeaders)
        def responseEntity = restTemplate.exchange("/api/auth/login", HttpMethod.POST, httpEntity, String.class)

        then:
        responseEntity.statusCode == HttpStatus.OK
        def documentContext = JsonPath.parse(responseEntity.getBody())

        documentContext.read('$.message') == "로그인 성공"
    }
    @Autowired
    MockMvc mockMvc

    @WithMockUser(username="testUsername")
    def "닉네임 변경 정상 케이스"() {

        given:
        objectMapper = new ObjectMapper()
        httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)
        def username = "testUsername"
        def nickname = "testNickname1"

        def request = new MemberUpdateNicknameRequest()
        request.setNicknameForTest(nickname)

        def member = Member.builder()
                        .id(10L)
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

    @WithMockUser(username="testUsername")
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
                .id(10L)
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
