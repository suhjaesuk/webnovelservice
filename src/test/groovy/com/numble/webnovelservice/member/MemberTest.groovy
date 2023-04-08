package com.numble.webnovelservice.member

import com.numble.webnovelservice.member.entity.Member
import spock.lang.Specification
import spock.lang.Subject

class MemberTest extends Specification {

    @Subject
    Member member

    void setup() {
        member = Member.builder()
                .username("testUsername")
                .password("testPassword")
                .nickname("testNickname")
                .profileImage("testProfileImage")
                .pointAmount(10000)
                .ticketCount(100)
                .build();
    }


    def "Member 생성 시 정상케이스"() {

        given:
        def username = "testUser"
        def password = "testPassword"
        def nickname = "testNickname"
        def profileImage = "testProfileImage"
        def pointAmount = 10000
        def ticketCount = 100

        when: //setup()에서 받아오기 때문에 비어있다.
        null

        then:
        member.username == username
        member.password == password
        member.nickname == nickname
        member.profileImage == profileImage
        member.pointAmount == pointAmount
        member.ticketCount == ticketCount
    }
}
