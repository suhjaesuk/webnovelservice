package com.numble.webnovelservice.member.repository

import com.numble.webnovelservice.member.entity.Member
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification
import spock.lang.Subject

class MemberRepositorySpec extends Specification {

    @Subject
    MemberRepository memberRepository

    @Autowired

    void setup() {
        memberRepository = Mock(MemberRepository)
    }

    def "Member 생성 시 정상케이스"() {
        given:
        def username = "testUsername"
        def password = "testPassword"
        def nickname = "testNickname"
        def profileImage = "testProfileImage"
        def pointAmount = 0
        def ticketCount = 0

        Member expectedMember = Member.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .profileImage(profileImage)
                .pointAmount(pointAmount)
                .ticketCount(ticketCount)
                .build()

        when:
        Member savedMember = memberRepository.save(expectedMember)

        then:
        savedMember.id == 1L
        savedMember.username == expectedMember.username
        savedMember.password == expectedMember.password
        savedMember.nickname == expectedMember.nickname
        savedMember.profileImage == expectedMember.profileImage
        savedMember.pointAmount == expectedMember.pointAmount
        savedMember.ticketCount == expectedMember.ticketCount
    }
}
