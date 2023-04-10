package com.numble.webnovelservice.member.dto.request;

import lombok.Getter;

@Getter
public class MemberUpdateNicknameRequest {

    private String nickname;

    public void setNicknameForTest(String nickname) {
        this.nickname = nickname;
    }
}
