package com.numble.webnovelservice.member.repository

import com.numble.webnovelservice.member.entity.Member
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class MemberRepositoryTest extends Specification {

    @Autowired
    MemberRepository memberRepository

    def "Member 생성 시 정상케이스"() {

        given:
        def username = "testUsername"
        def nickname = "testNickname"
        def password = "testPassword"
        def profileImage = "testProfileImage"
        def pointAmount = 0
        def ticketCount = 0

        Member expectedMember = Member.builder()
                            .username(username)
                            .nickname(nickname)
                            .password(password)
                            .profileImage(profileImage)
                            .pointAmount(pointAmount)
                            .ticketCount(ticketCount)
                            .build()


        when:
        Member member = memberRepository.save(expectedMember)

        then:
        member.getUsername() == username
        member.getNickname() == nickname
        member.getPassword() == password
        member.getProfileImage() == profileImage
        member.getPointAmount() == pointAmount
        member.getTicketCount() == ticketCount
    }

    def "Member 생성 시 id가 null일 경우 예외가 발생해야한다."() {

        given:
        def username = null
        def nickname = "testNickname"
        def password = "testPassword"
        def profileImage = "testProfileImage"
        def pointAmount = 0
        def ticketCount = 0

        Member expectedMember = Member.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .profileImage(profileImage)
                .pointAmount(pointAmount)
                .ticketCount(ticketCount)
                .build()

        when:
        memberRepository.save(expectedMember)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "Member 생성 시 nickname이 null일 경우 예외가 발생한다."() {

        given:
        def username = "testUsername"
        def nickname = null
        def password = "testPassword"
        def profileImage = "testProfileImage"
        def pointAmount = 0
        def ticketCount = 0

        Member expectedMember = Member.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .profileImage(profileImage)
                .pointAmount(pointAmount)
                .ticketCount(ticketCount)
                .build()

        when:
        memberRepository.save(expectedMember)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "Member 생성 시 password가 null일 경우 예외가 발생해야한다."() {

        given:
        def username = "testUsername"
        def nickname = "testNickname"
        def password = null
        def profileImage = "testProfileImage"
        def pointAmount = 0
        def ticketCount = 0

        Member expectedMember = Member.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .profileImage(profileImage)
                .pointAmount(pointAmount)
                .ticketCount(ticketCount)
                .build()

        when:
        memberRepository.save(expectedMember)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "Member 생성 시 profileImage가 null일 경우 예외가 발생해야한다."() {

        given:
        def username = "testUsername"
        def nickname = "testNickname"
        def password = "testPassword"
        def profileImage = null
        def pointAmount = 0
        def ticketCount = 0

        Member expectedMember = Member.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .profileImage(profileImage)
                .pointAmount(pointAmount)
                .ticketCount(ticketCount)
                .build()

        when:
        memberRepository.save(expectedMember)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "Member 생성 시 pointAmount가 null일 경우 예외가 발생해야한다."() {

        given:
        def username = "testUsername"
        def nickname = "testNickname"
        def password = "testPassword"
        def profileImage = "testProfileImage"
        def pointAmount = null
        def ticketCount = 0

        Member expectedMember = Member.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .profileImage(profileImage)
                .pointAmount(pointAmount)
                .ticketCount(ticketCount)
                .build()

        when:
        memberRepository.save(expectedMember)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "Member 생성 시 ticketCount null일 경우 예외가 발생해야한다."() {

        given:
        def username = "testUsername"
        def nickname = "testNickname"
        def password = "testPassword"
        def profileImage = "testProfileImage"
        def pointAmount = 0
        def ticketCount = null

        Member expectedMember = Member.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .profileImage(profileImage)
                .pointAmount(pointAmount)
                .ticketCount(ticketCount)
                .build()

        when:
        memberRepository.save(expectedMember)

        then:
        thrown(DataIntegrityViolationException)
    }
}
