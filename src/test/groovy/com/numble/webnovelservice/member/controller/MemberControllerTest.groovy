package com.numble.webnovelservice.member.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import com.numble.webnovelservice.member.dto.request.MemberLoginRequest
import com.numble.webnovelservice.member.dto.request.MemberSignUpRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
}