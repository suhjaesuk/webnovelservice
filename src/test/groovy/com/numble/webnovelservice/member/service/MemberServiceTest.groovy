package com.numble.webnovelservice.member.service

import com.numble.webnovelservice.common.exception.WebNovelServiceException
import com.numble.webnovelservice.member.dto.request.MemberLoginRequest
import com.numble.webnovelservice.member.dto.request.MemberSignUpRequest
import com.numble.webnovelservice.member.entity.Member
import com.numble.webnovelservice.member.repository.MemberRepository
import com.numble.webnovelservice.util.jwt.JwtUtil
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import javax.servlet.http.HttpServletResponse

class MemberServiceTest extends Specification{

    def "회원가입 시 정상 케이스"(){

        given:
        def memberRepository = Mock(MemberRepository.class)
        def passwordEncoder = Mock(PasswordEncoder.class)
        def jwtUtil = Mock(JwtUtil.class)
        def memberService = new MemberService(memberRepository, passwordEncoder, jwtUtil)

        String username = "testUsername"
        String nickname = "testNickname"
        String password = "testPassword"
        String encryptedPassword = "encryptedPassword"

        MemberSignUpRequest request = new MemberSignUpRequest()
        request.setUsernameForTest(username)
        request.setNicknameForTest(nickname)
        request.setPasswordForTest(password)
        Member member = request.toMember(encryptedPassword)

        //stub
        passwordEncoder.encode(request.getPassword()) >> encryptedPassword
        memberRepository.existsByUsername(username) >> false
        memberRepository.save(member) >> member

        when:
        memberService.signUp(request)

        then:
        1* memberRepository.save(_)
    }

    def "회원가입 시 중복된 회원아이디가 있을 경우 예외가 발생한다."(){

        given:
        def memberRepository = Mock(MemberRepository.class)
        def passwordEncoder = Mock(PasswordEncoder.class)
        def jwtUtil = Mock(JwtUtil.class)
        def memberService = new MemberService(memberRepository, passwordEncoder, jwtUtil)

        def username = "testUsername"
        def nickname = "testNickname"
        def password = "testPassword"
        def encryptedPassword = "encryptedPassword"

        def request = new MemberSignUpRequest()
        request.setUsernameForTest(username)
        request.setNicknameForTest(nickname)
        request.setPasswordForTest(password)
        def member = request.toMember(encryptedPassword)

        //stub
        passwordEncoder.encode(request.getPassword()) >> encryptedPassword
        memberRepository.existsByUsername(username) >> true
        memberRepository.save(member) >> member

        when:
        memberService.signUp(request)

        then:
        thrown(WebNovelServiceException)
    }

    def "로그인 시 정상 케이스"(){

        given:
        def memberRepository = Mock(MemberRepository.class)
        def passwordEncoder = Mock(PasswordEncoder.class)
        def jwtUtil = Mock(JwtUtil.class)
        def memberService = new MemberService(memberRepository, passwordEncoder, jwtUtil)

        def username = "testUsername"
        def password = "testPassword"

        Member member = Member.builder()
                            .username(username)
                            .password(password)
                            .build()

        def request = new MemberLoginRequest()
        request.setUsernameForTest(username)
        request.setPasswordForTest(password)

        def response = Mock(HttpServletResponse.class)

        //stub
        memberRepository.findByUsername(username)>>Optional.of(member)
        passwordEncoder.matches(request.getPassword(), member.getPassword()) >> true

        when:
        memberService.login(request, response)

        then:
        1 * response.addHeader('AccessToken',_)
    }

    def "로그인 시 회원을 찾지 못할 경우 예외가 발생한다"(){

        given:
        def memberRepository = Mock(MemberRepository.class)
        def passwordEncoder = Mock(PasswordEncoder.class)
        def jwtUtil = Mock(JwtUtil.class)
        def memberService = new MemberService(memberRepository, passwordEncoder, jwtUtil)

        def username = "testUsername"
        def password = "testPassword"

        Member member = Member.builder()
                .username(username)
                .password(password)
                .build()

        def request = new MemberLoginRequest()
        request.setUsernameForTest(username)
        request.setPasswordForTest(password)

        def response = Mock(HttpServletResponse.class)

        //stub
        memberRepository.findByUsername(username)>> null
        passwordEncoder.matches(request.getPassword(), member.getPassword()) >> true

        when:
        memberService.login(request, response)

        then:
        thrown(NullPointerException)
    }

    def "로그인 시 비밀번호가 다를경우 예외가 발생한다"(){

        given:
        def memberRepository = Mock(MemberRepository.class)
        def passwordEncoder = Mock(PasswordEncoder.class)
        def jwtUtil = Mock(JwtUtil.class)
        def memberService = new MemberService(memberRepository, passwordEncoder, jwtUtil)

        def username = "testUsername"
        def password = "testPassword"

        Member member = Member.builder()
                .username(username)
                .password(password)
                .build()

        def request = new MemberLoginRequest()
        request.setUsernameForTest(username)
        request.setPasswordForTest(password)

        def response = Mock(HttpServletResponse.class)

        //stub
        memberRepository.findByUsername(username)>> Optional.of(member)
        passwordEncoder.matches(request.getPassword(), member.getPassword()) >> false

        when:
        memberService.login(request, response)

        then:
        thrown(WebNovelServiceException)
    }


}
