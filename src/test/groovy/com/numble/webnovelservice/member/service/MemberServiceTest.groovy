package com.numble.webnovelservice.member.service

import com.numble.webnovelservice.common.exception.WebNovelServiceException
import com.numble.webnovelservice.member.dto.request.MemberLoginRequest
import com.numble.webnovelservice.member.dto.request.MemberSignUpRequest
import com.numble.webnovelservice.member.dto.request.MemberUpdateNicknameRequest
import com.numble.webnovelservice.member.dto.request.MemberUpdateProfileImageRequest
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
        memberRepository.existsByNickname(username) >> false
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
        memberRepository.existsByNickname(username) >> false
        memberRepository.save(member) >> member

        when:
        memberService.signUp(request)

        then:
        thrown(WebNovelServiceException)
    }

    def "회원가입 시 중복된 닉네임이 있을 경우 예외가 발생한다."(){

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
        memberRepository.existsByUsername(username) >> false
        memberRepository.existsByNickname(nickname) >> true
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
        memberRepository.findByUsername(username) >> Optional.of(member)
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
        memberRepository.findByUsername(username) >> null
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
        memberRepository.findByUsername(username) >> Optional.of(member)
        passwordEncoder.matches(request.getPassword(), member.getPassword()) >> false

        when:
        memberService.login(request, response)

        then:
        thrown(WebNovelServiceException)
    }

    def "닉네임 변경 시 정상 케이스"(){

        given:
        def memberRepository = Mock(MemberRepository.class)
        def passwordEncoder = Mock(PasswordEncoder.class)
        def jwtUtil = Mock(JwtUtil.class)
        def memberService = new MemberService(memberRepository, passwordEncoder, jwtUtil)

        def nickname = "testNickname"
        def id = 1L

        Member member = Member.builder()
                .id(id)
                .nickname(nickname)
                .build()

        def request = new MemberUpdateNicknameRequest()
        request.setNicknameForTest(nickname)

        //stub
        memberRepository.existsByNickname(nickname) >> false
        memberRepository.findById(member.getId()) >> Optional.of(member)

        when:
        memberService.updateNickname(member, request)
        def findMemberOptional = memberRepository.findById(member.getId())
        def findMember = findMemberOptional.get()

        then:
        findMember.getNickname() == nickname
    }

    def "닉네임 변경 시 회원을 못찾을 경우 예외가 발생한다"(){

        given:
        def memberRepository = Mock(MemberRepository.class)
        def passwordEncoder = Mock(PasswordEncoder.class)
        def jwtUtil = Mock(JwtUtil.class)
        def memberService = new MemberService(memberRepository, passwordEncoder, jwtUtil)

        def nickname = "testNickname"
        def id = 1L

        Member member = Member.builder()
                .id(id)
                .nickname(nickname)
                .build()

        def request = new MemberUpdateNicknameRequest()
        request.setNicknameForTest(nickname)

        //stub
        memberRepository.existsByNickname(nickname) >> false
        memberRepository.findById(member.getId()) >> null

        when:
        memberService.updateNickname(member, request)

        then:
        thrown(NullPointerException)
    }

    def "닉네임 변경 시 닉네임 중복일 경우 예외가 발생한다"(){

        given:
        def memberRepository = Mock(MemberRepository.class)
        def passwordEncoder = Mock(PasswordEncoder.class)
        def jwtUtil = Mock(JwtUtil.class)
        def memberService = new MemberService(memberRepository, passwordEncoder, jwtUtil)

        def nickname = "testNickname"
        def id = 1L

        Member member = Member.builder()
                .id(id)
                .nickname(nickname)
                .build()

        def request = new MemberUpdateNicknameRequest()
        request.setNicknameForTest(nickname)

        //stub
        memberRepository.existsByNickname(nickname) >> true
        memberRepository.findById(member.getId()) >> Optional.of(member)

        when:
        memberService.updateNickname(member, request)

        then:
        thrown(WebNovelServiceException)
    }

    def "프로필이미지 변경 시 정상 케이스"(){

        given:
        def memberRepository = Mock(MemberRepository.class)
        def passwordEncoder = Mock(PasswordEncoder.class)
        def jwtUtil = Mock(JwtUtil.class)
        def memberService = new MemberService(memberRepository, passwordEncoder, jwtUtil)

        def profileImage = "testProfileImage"
        def id = 1L

        Member member = Member.builder()
                .id(id)
                .profileImage(profileImage)
                .build()

        def request = new MemberUpdateProfileImageRequest()
        request.setProfileImageForTest(profileImage)

        //stub
        memberRepository.findById(member.getId()) >> Optional.of(member)

        when:
        memberService.updateProfileImage(member, request)
        def findMemberOptional = memberRepository.findById(member.getId())
        def findMember = findMemberOptional.get()

        then:
        findMember.getProfileImage() == profileImage
    }

    def "프로필이미지 변경 시 회원을 못 찾을 경우 예외가 발생한다."(){

        given:
        def memberRepository = Mock(MemberRepository.class)
        def passwordEncoder = Mock(PasswordEncoder.class)
        def jwtUtil = Mock(JwtUtil.class)
        def memberService = new MemberService(memberRepository, passwordEncoder, jwtUtil)

        def profileImage = "testProfileImage"
        def id = 1L

        Member member = Member.builder()
                .id(id)
                .profileImage(profileImage)
                .build()

        def request = new MemberUpdateProfileImageRequest()
        request.setProfileImageForTest(profileImage)

        //stub
        memberRepository.findById(member.getId()) >> null

        when:
        memberService.updateProfileImage(member, request)

        then:
        thrown(NullPointerException)
    }
}
