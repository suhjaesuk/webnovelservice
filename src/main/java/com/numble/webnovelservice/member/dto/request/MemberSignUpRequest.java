package com.numble.webnovelservice.member.dto.request;

import com.numble.webnovelservice.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberSignUpRequest {

    private String username;
    private String nickname;
    private String password;

    /*
    password 변수는 MemberService에서 암호화 후 매개변수를 통해 DB에 저장한다.
     */
    public Member toMember(String encryptedPassword){
        return Member.builder()
                .username(username)
                .nickname(nickname)
                .password(encryptedPassword)
                .profileImage("profileImage")
                .pointAmount(0)
                .ticketCount(0)
                .build();
    }
}
